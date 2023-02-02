package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearporjecttoletapp.Adapter.FamilyHomeAdapter;
import com.example.finalyearporjecttoletapp.Model.HomeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    String l,fa,sa,s,b,ba,searchDistrict,searchDivision,searchArea;
    int pr1,pr2,count = 0;
    RecyclerView searchRecyclerView;
    ArrayList<HomeModel> list;
    DatabaseReference reference;
    FirebaseDatabase database;
    FamilyHomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("HomeInfo");


        Intent intent = getIntent();
       /* l = intent.getStringExtra("Location");
        fa = intent.getStringExtra("FAmount");
        pr1 = Integer.parseInt(fa);
        sa = intent.getStringExtra("SAmount");
        pr2 = Integer.parseInt(sa);
        s = intent.getStringExtra("PropertyType");
        b = intent.getStringExtra("bedRoom");
        ba = intent.getStringExtra("bathRoom");*/

        searchDivision = intent.getStringExtra("division");
        searchDistrict = intent.getStringExtra("district");
        searchArea = intent.getStringExtra("area");
        //Toast.makeText(this, searchDivision, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,searchDistrict, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,searchArea, Toast.LENGTH_SHORT).show();


        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<HomeModel>();

        adapter = new FamilyHomeAdapter(list,this);
        searchRecyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    HomeModel homeModel = dataSnapshot.getValue(HomeModel.class);
                   // int p = Integer.parseInt(homeModel.getPrice());
                   /* if(homeModel.getLocation().trim().equals(l) && (p>=pr1 || p<=pr2) && homeModel.getBedRoom().equals(b) && homeModel.getBathRoom().equals(ba) && homeModel.getPropertyType().equals(s)){
                        list.add(homeModel);
                        count = 1;

                    }*/

                   /* if(homeModel.getDivision().equals(searchDivision) && homeModel.getDistrict().equals(searchDistrict) && homeModel.getArea().equals(searchArea)){
                        list.add(homeModel);
                        count = 1;

                    }*/
                  //  String dis = homeModel.getArea();
                   // Toast.makeText(SearchResultActivity.this,dis, Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(SearchResultActivity.this,searchArea, Toast.LENGTH_SHORT).show();

                    /*if (dis.equals("Dhaka")){
                        Toast.makeText(SearchResultActivity.this, "find", Toast.LENGTH_SHORT).show();
                        count=1;
                    }*/
                    //String sr="Gazipur";
                    if(homeModel.getDistrict().equals(searchDistrict) && homeModel.getDivision().equals(searchDivision) && homeModel.getArea().equals(searchArea)) {
                        list.add(homeModel);
                        count = 1;
                   }


                }
                adapter.notifyDataSetChanged();

                if(count == 0){

                        Toast.makeText(SearchResultActivity.this, "No Result Find", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(SearchResultActivity.this,SearchActivity.class);
                        startActivity(intent1);
                }
                else {
                    count = 0;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchResultActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}