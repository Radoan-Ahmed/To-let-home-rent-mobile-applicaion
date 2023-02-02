package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearporjecttoletapp.Adapter.MessageAdapter;
import com.example.finalyearporjecttoletapp.Model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagingActivity extends AppCompatActivity {

    RecyclerView messageRecyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<MessageModel> list;
    MessageAdapter adapter;
    FirebaseAuth auth;
    FirebaseUser user;
    String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        getSupportActionBar().setTitle("Chats");


        messageRecyclerView = findViewById(R.id.meassageRecyclerView);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("User");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        currentUserId = user.getUid();

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list =  new ArrayList<MessageModel>();
        adapter = new MessageAdapter(list,this);
        messageRecyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    if(!currentUserId.equals(messageModel.getUserId())) {
                        list.add(messageModel);
                    }


                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}