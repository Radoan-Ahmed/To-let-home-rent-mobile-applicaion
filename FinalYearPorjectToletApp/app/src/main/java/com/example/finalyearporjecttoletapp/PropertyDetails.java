package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearporjecttoletapp.Adapter.GetImageAdapter;
import com.example.finalyearporjecttoletapp.Model.GetImageModel;
import com.example.finalyearporjecttoletapp.Model.HomeModel;
import com.example.finalyearporjecttoletapp.Model.PostHomeModel;
import com.example.finalyearporjecttoletapp.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PropertyDetails extends AppCompatActivity {

    ImageView image,phoneCall,mapView;
    RecyclerView getImageRecyclerView;

    GetImageAdapter getImageAdapter;
    ArrayList<GetImageModel> arrayList;

    TextView bedRoom,bathRoom,kitchen,locationName,Name,totalAmount,propertyType,locationAddress,locDivision,locDistrict,loctArea;
    FirebaseDatabase database;
    DatabaseReference reference,reference1,reference3;
    FirebaseUser user;
    FirebaseAuth auth;
    String str,userId,UserId,name,picture,phoneNumber;
    CircleImageView profile;
    ImageView message;
    static  int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        getSupportActionBar().setTitle("Property Details");

        str = getIntent().getStringExtra("Key");
        UserId = getIntent().getStringExtra("UserId");
      //  image = findViewById(R.id.picture);
        bedRoom = findViewById(R.id.bedRoom);
        bathRoom = findViewById(R.id.bathRoom);
        kitchen = findViewById(R.id.kitchen);
        message = findViewById(R.id.message);
        phoneCall = findViewById(R.id.phoneCall);
        locationName = findViewById(R.id.locationName);
        profile = findViewById(R.id.profile);
        Name = findViewById(R.id.Name);
        locDivision = findViewById(R.id.locDivision);
        locDistrict = findViewById(R.id.locDistrict);
        loctArea = findViewById(R.id.locArea);
        totalAmount = findViewById(R.id.totalAmount);
        propertyType = findViewById(R.id.propertyType);
       // locationAddress = findViewById(R.id.locationAddress);

        getImageRecyclerView = findViewById(R.id.getimage_recyclerview);

        mapView = findViewById(R.id.mapView);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();

        database = FirebaseDatabase.getInstance();


        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertyDetails.this,MapLocation.class);
                intent.putExtra("key",str);
                startActivity(intent);
            }
        });


        getImageRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        arrayList = new ArrayList<>();
        getImageAdapter = new GetImageAdapter(this,arrayList);
        getImageRecyclerView.setAdapter(getImageAdapter);

        reference3 = FirebaseDatabase.getInstance().getReference().child("ImageName").child(str);
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    GetImageModel getImageModel = dataSnapshot.getValue(GetImageModel.class);
                    arrayList.add(getImageModel);

                }
                getImageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference1 = FirebaseDatabase.getInstance().getReference("User").child(UserId);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);

                name = userModel.getName();
                picture = userModel.getImage();
                phoneNumber = userModel.getPhoneNumber();

                Name.setText(userModel.getName());

                if(userModel.getImage().equals("default")){
                    profile.setImageResource(R.drawable.ic_baseline_person_24);
                }
                else {

                    String picture = null;
                    picture = userModel.getImage();
                    Picasso.get().load(picture).into(profile);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        reference = database.getReference().child("HomeInfo").child(str);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HomeModel homeModel = snapshot.getValue(HomeModel.class);

                bedRoom.setText(homeModel.getBedRoom());
                bathRoom.setText(homeModel.getBathRoom());
                kitchen.setText(homeModel.getKitchenRoom());
                locationName.setText(homeModel.getLocation());
                totalAmount.setText(homeModel.getPrice());
                propertyType.setText(homeModel.getPropertyType());
                locDivision.setText(homeModel.getDivision());
                locDistrict.setText(homeModel.getDistrict());
                loctArea.setText(homeModel.getArea());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertyDetails.this,ChatActivity.class);
                intent.putExtra("UserId",UserId);
                intent.putExtra("Name",name);
                intent.putExtra("Image",picture);
                startActivity(intent);
                finish();

            }
        });

        phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(PropertyDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PropertyDetails.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
                }
                else {

                }

                if(phoneNumber.isEmpty()){
                    Toast.makeText(PropertyDetails.this, "Phone Number not found", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:"+phoneNumber));
                    startActivity(i);
                }

            }
        });



    }
}