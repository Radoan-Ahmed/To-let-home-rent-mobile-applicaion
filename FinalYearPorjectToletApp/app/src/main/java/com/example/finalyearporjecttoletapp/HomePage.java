package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearporjecttoletapp.Adapter.FamilyHomeAdapter;
import com.example.finalyearporjecttoletapp.Adapter.HomeAdapter;
import com.example.finalyearporjecttoletapp.Model.FamilyHomeModel;
import com.example.finalyearporjecttoletapp.Model.HomeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity{



    BottomNavigationView bottomNavigation;
    TextView searchView;

    RecyclerView recyclerView1,recyclerView2,recyclerView3;
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference reference,vcref;
    FirebaseDatabase database;
    FamilyHomeAdapter adapter,adapter1,adapter2;
    HomeModel model;
    ArrayList<HomeModel> list,list1,list2;
    String userId;
    ImageView saveHome;
    boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();
        database = FirebaseDatabase.getInstance();

        saveHome = findViewById(R.id.saveHome);



        reference = database.getReference().child("HomeInfo");


        BachelorHome();


        bottomNavigation = findViewById(R.id.bottomNavigation);
        searchView = findViewById(R.id.searchView);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this,SearchActivity.class);
                startActivity(intent);
            }
        });




        bottomNavigation.setSelectedItemId(R.id.home);
        bottomNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:

                    break;
                case R.id.map:
                    Intent intent3 = new Intent(HomePage.this,MapsActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.chat:
                    Intent intent = new Intent(HomePage.this,MessagingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.saved:
                    Intent intent2 = new Intent(HomePage.this, SaveHome.class);
                    startActivity(intent2);
                    break;
                case R.id.profie:
                    Intent intent1 = new Intent(HomePage.this,ProfilePage.class);
                    startActivity(intent1);
                    break;
            }

            return true;
        });


        vcref = database.getReference("vc");
        vcref.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String suid = snapshot.child("callerid").getValue().toString();
                    Intent intent = new Intent(HomePage.this,VideoCallinComming.class);
                    intent.putExtra("suid",suid);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void BachelorHome() {

        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView2 = findViewById(R.id.recyclerView2);


        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        linearLayoutManager3.setReverseLayout(true);
        linearLayoutManager3.setStackFromEnd(true);

        recyclerView3.setLayoutManager(linearLayoutManager3);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);

        recyclerView1.setLayoutManager(linearLayoutManager1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);

        recyclerView2.setLayoutManager(linearLayoutManager2);


        list = new ArrayList<HomeModel>();

        list1 = new ArrayList<HomeModel>();

        list2 = new ArrayList<HomeModel>();


        adapter = new FamilyHomeAdapter(list,this);
        adapter1 = new FamilyHomeAdapter(list1,this);
        adapter2 = new FamilyHomeAdapter(list2,this);

        recyclerView3.setAdapter(adapter);
        recyclerView1.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    HomeModel homeModel = dataSnapshot.getValue(HomeModel.class);
                    if(homeModel.getPropertyType().equals("bachelor")){
                        list.add(homeModel);
                    }
                    if(homeModel.getPropertyType().equals("family")){
                        list1.add(homeModel);
                    }
                    if(homeModel.getPropertyType().equals("sublet")){
                        list2.add(homeModel);
                    }
                }
                adapter.notifyDataSetChanged();

                adapter1.notifyDataSetChanged();

                adapter2.notifyDataSetChanged();

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