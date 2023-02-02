package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.finalyearporjecttoletapp.Model.VcModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.URL;

public class VideoCallOutgoing extends AppCompatActivity {

    FloatingActionButton declinebtn;
    String recever_token,recever_uid,type,senderUserId;
    DatabaseReference reference,documentReference,responceref,cancelRef,videoCallRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user;
    VcModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call_outgoing);

        declinebtn = findViewById(R.id.decline_vc_og);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            recever_uid = bundle.getString("uid");
        }
        else {
            Toast.makeText(this, "Data missing", Toast.LENGTH_SHORT).show();
        }

        user = FirebaseAuth.getInstance().getCurrentUser();
        senderUserId = user.getUid();

        fetchReceverData(recever_uid);

        call(senderUserId,recever_uid);

        callResponce(senderUserId,recever_uid);

        declinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancleVC(senderUserId,recever_uid);
            }
        });


    }

    private void cancleVC(String senderUserId, String recever_uid) {

        model = new VcModel();
        cancelRef = database.getReference("cancel");
        videoCallRef = FirebaseDatabase.getInstance().getReference("vc").child(recever_uid);
        try {
            videoCallRef.removeValue();
            model.setResponce("no");
            cancelRef.child(senderUserId).setValue(model);

            Toast.makeText(this, "Call Cancel", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(VideoCallOutgoing.this,ChatActivity.class);
            startActivity(intent);
            finish();


        }catch (Exception e){

        }

    }




    private void fetchReceverData(String recever_uid) {


    }

    private void call(String senderUserId, String recever_uid) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("vc").child(recever_uid).child("callerid");
        reference.setValue(senderUserId);

    }


    private void callResponce(String senderUserId, String recever_uid) {

        responceref = database.getReference("vcref").child(senderUserId).child(recever_uid);
        responceref.child("res").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String key = snapshot.child("key").getValue().toString();
                    String responce = snapshot.child("responce").getValue().toString();

                    if(responce.equals("yes")){
                        joinmeeting(key);
                        Toast.makeText(VideoCallOutgoing.this, "Call Accepted", Toast.LENGTH_SHORT).show();
                    }
                    else if(responce.equals("no")){
                        responceref.removeValue();
                        Toast.makeText(VideoCallOutgoing.this, "Call Denied", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VideoCallOutgoing.this,ChatActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
                else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void joinmeeting(String key) {

        try {

            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://meet.jit.si"))
                    .setRoom(key)
                    .setAudioMuted(false)
                    .setAudioOnly(true)
                    .build();
            JitsiMeetActivity.launch(VideoCallOutgoing.this,options);
            finish();

        }catch (Exception e){

            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

        }

    }


}