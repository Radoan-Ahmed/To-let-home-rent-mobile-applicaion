package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearporjecttoletapp.Adapter.ChatAdapter;
import com.example.finalyearporjecttoletapp.Model.ChatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth auth;
    TextView UserName;
    CircleImageView messageProfilePicture;
    RecyclerView chatRcyclerView;
    ImageView send,backArrow,videoBtn,callBtn;
    EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        UserName = findViewById(R.id.UserName);
        messageProfilePicture = findViewById(R.id.messageProfilePicture);
        chatRcyclerView= findViewById(R.id.chatRcyclerView);
        send= findViewById(R.id.send);
        etMessage = findViewById(R.id.etMessage);
        backArrow = findViewById(R.id.backArrow);
        videoBtn = findViewById(R.id.videoBtn);
        callBtn = findViewById(R.id.callBtn);

        final String senderId = auth.getUid();
        String receverId = getIntent().getStringExtra("UserId");
        String name = getIntent().getStringExtra("Name");
        String image = getIntent().getStringExtra("Image");
        final String senderRoom = senderId+receverId;
        final String receverRoom = receverId+senderId;
        UserName.setText(name);

        if(image.equals("default")){
            messageProfilePicture.setImageResource(R.drawable.ic_baseline_person_24);
        }
        else{
            Picasso.get().load(image).into(messageProfilePicture);
        }

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this,MessagingActivity.class);
                startActivity(intent);
                finish();
            }
        });


         final ArrayList<ChatModel> chatModels = new ArrayList<ChatModel>();
         final ChatAdapter chatAdapter = new ChatAdapter(chatModels,this);


        chatRcyclerView.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRcyclerView.setLayoutManager(layoutManager);

        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModels.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                    chatModels.add(chatModel);
                }

                chatAdapter.notifyDataSetChanged();
                chatRcyclerView.smoothScrollToPosition(chatRcyclerView.getAdapter().getItemCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();
                final  ChatModel chatModel = new ChatModel(senderId,message);
                chatModel.setTime(new Date().getTime());
                etMessage.setText("");

                database.getReference().child("chats").child(senderRoom).push().setValue(chatModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                database.getReference().child("chats").child(receverRoom)
                                        .push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });

                            }
                        });

            }
        });



        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(ChatActivity.this,VideoCallOutgoing.class);
                intent.putExtra("uid",receverId);
                intent.putExtra("senderId",senderId);
                startActivity(intent);
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ChatActivity.this,VideoCallOutgoing.class);
                intent.putExtra("uid",receverId);
                intent.putExtra("senderId",senderId);
                startActivity(intent);
            }
        });



    }
}