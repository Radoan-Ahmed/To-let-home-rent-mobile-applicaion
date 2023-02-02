package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalyearporjecttoletapp.Adapter.FamilyHomeAdapter;
import com.example.finalyearporjecttoletapp.Adapter.HomeAdapter;
import com.example.finalyearporjecttoletapp.Adapter.MyPostAdapter;
import com.example.finalyearporjecttoletapp.Model.HomeModel;
import com.example.finalyearporjecttoletapp.Model.PostHomeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPost extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseDatabase database;
    MyPostAdapter adapter;
    BottomNavigationView bottomNavigation;
    ArrayList<PostHomeModel> list;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        getSupportActionBar().setTitle("MY Post");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();



        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.profie);
        bottomNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    Intent intent = new Intent(MyPost.this,HomePage.class);
                    startActivity(intent);
                    break;
                case R.id.profie:
                    Intent intent1 = new Intent(MyPost.this,ProfilePage.class);
                    startActivity(intent1);
                    break;

                case R.id.saved:
                    Intent intent2 = new Intent(MyPost.this,SaveHome.class);
                    startActivity(intent2);
                    break;
            }

            return true;
        });




        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("HomeInfo");

        recyclerView = findViewById(R.id.myPostRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<PostHomeModel>();
        adapter = new MyPostAdapter(list,this);
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PostHomeModel homeModel = dataSnapshot.getValue(PostHomeModel.class);
                    if(homeModel.getUserId().equals(userId)){
                        list.add(homeModel);
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