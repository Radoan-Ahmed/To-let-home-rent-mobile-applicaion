package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
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

public class VideoCallinComming extends AppCompatActivity {

    FloatingActionButton decline_vc_ic,accept_vc_ic;
    String senderUid, receverUid, senderToken;
    VcModel model;
    MediaPlayer mp;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference,videocallRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_callin_comming);

        decline_vc_ic = findViewById(R.id.decline_vc_ic);
        accept_vc_ic = findViewById(R.id.accept_vc_ic);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        receverUid = user.getUid();
        model = new VcModel();

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            senderUid = bundle.getString("suid");
        }
        else {
            Toast.makeText(this, "Data missing", Toast.LENGTH_SHORT).show();
        }


        reference = database.getReference("vcref").child(senderUid).child(receverUid);
        videocallRef = FirebaseDatabase.getInstance().getReference("vc");


        try {

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            mp = MediaPlayer.create(getApplicationContext(),notification);
            mp.start();

        }catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                checkCallStatus();
                fetchcallerData(senderUid);

            }
        },1000);

        accept_vc_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String responce = "yes";
                sendResponce(responce);
                videocallRef.removeValue();
                mp.stop();
            }
        });

        decline_vc_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videocallRef.removeValue();
                String responce = "no";
                mp.stop();
                sendResponce(responce);
                Intent intent = new Intent(VideoCallinComming.this,MessagingActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }

    private void sendResponce(String responce) {

        if(responce.equals("yes")){
            model.setKey("riyad"+receverUid);
            model.setResponce("yes");
            reference.child("res").setValue(model);
            joinmeeting();

            Handler handler =  new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    reference.removeValue();

                }
            },1000);

        }
        else if(responce.equals("no")){
            model.setKey("riyad"+receverUid);
            model.setResponce("no");
            reference.child("res").setValue(model);

            Handler handler =  new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    reference.removeValue();

                }
            },1000);

        }

    }

    private void joinmeeting() {

        try {

            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://meet.jit.si"))
                    .setRoom("riyad"+receverUid)
                    .setAudioMuted(false)
                    .setAudioOnly(true)
                    .build();
            JitsiMeetActivity.launch(VideoCallinComming.this,options);
            finish();

        }catch (Exception e){

            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

        }

    }

    private void fetchcallerData(String senderUid) {
    }

    private void checkCallStatus() {

        DatabaseReference cancelRef;
        cancelRef = FirebaseDatabase.getInstance().getReference("cancel");
        cancelRef.child(senderUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String responce = snapshot.child("responce").getValue().toString();
                    if(responce.equals("no")){
                        cancelRef.removeValue();
                        Intent intent = new Intent(VideoCallinComming.this,HomePage.class);
                        startActivity(intent);
                        mp.stop();
                        finish();

                    }
                    else {

                    }
                }
                else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}