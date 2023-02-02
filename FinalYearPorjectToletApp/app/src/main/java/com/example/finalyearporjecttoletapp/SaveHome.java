package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalyearporjecttoletapp.Adapter.FamilyHomeAdapter;
import com.example.finalyearporjecttoletapp.Adapter.HomeAdapter;
import com.example.finalyearporjecttoletapp.Adapter.MyPostAdapter;
import com.example.finalyearporjecttoletapp.Adapter.SaveHomeAdapter;
import com.example.finalyearporjecttoletapp.Model.HomeModel;
import com.example.finalyearporjecttoletapp.Model.PostHomeModel;
import com.example.finalyearporjecttoletapp.Model.SaveHomeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SaveHome extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    RecyclerView recyclerView;
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseDatabase database;
    SaveHomeAdapter adapter;
    ArrayList<SaveHomeModel> list;
    String userId;
    boolean isPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_home);

        getSupportActionBar().setTitle("Save Post");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("SaveHome");

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.saved);
        bottomNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    Intent intent = new Intent(SaveHome.this,HomePage.class);
                    startActivity(intent);
                    break;
                case R.id.profie:
                    Intent intent1 = new Intent(SaveHome.this,ProfilePage.class);
                    startActivity(intent1);
                    break;
                case R.id.chat:
                    Intent intent2 = new Intent(SaveHome.this,MessagingActivity.class);
                    startActivity(intent2);
                    break;
            }

            return true;
        });

        recyclerView = findViewById(R.id.saveRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<SaveHomeModel>();
        adapter = new SaveHomeAdapter(list,this);
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    SaveHomeModel homeModel = dataSnapshot.getValue(SaveHomeModel.class);
                    if(dataSnapshot.getKey().equals(homeModel.getKey().toString()+userId)){
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


    @Override
    public void onBackPressed() {
        if(isPressed){
            finishAffinity();
            System.exit(0);
        }
        else {
            Toast.makeText(this, "Please Click again to exit", Toast.LENGTH_SHORT).show();
            isPressed = true;
        }
    }

}