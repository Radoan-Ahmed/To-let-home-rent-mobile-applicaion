package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalyearporjecttoletapp.Model.UserModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePage extends AppCompatActivity{

    TextView username,useremail,myPost,logout,postHomeBt,personalDetails;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseStorage storage;
    DatabaseReference reference;
    BottomNavigationView bottomNavigation;
    String userId,userImage;
    CircleImageView profile_image,edit;
    Uri uri;

    ProgressDialog progressDialog;
    boolean isPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        getSupportActionBar().setTitle("Profile");

        username = findViewById(R.id.userName);
        useremail = findViewById(R.id.userEmail);
        myPost = findViewById(R.id.myPost);
        logout = findViewById(R.id.logout);
        personalDetails = findViewById(R.id.personalDetails);
        profile_image = findViewById(R.id.profile_image);
        edit = findViewById(R.id.edit);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();


        reference = FirebaseDatabase.getInstance().getReference("User").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                username.setText(userModel.getName());
                useremail.setText(userModel.getEmail());


                if(userModel.getImage().equals("default")){
                    profile_image.setImageResource(R.drawable.ic_baseline_person_24);
                }
                else {

                    String picture = null;
                    picture = userModel.getImage();
                    Picasso.get().load(picture).into(profile_image);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        bottomNavigation= findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.profie);
        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    Intent intent =  new Intent(ProfilePage.this,HomePage.class);
                    startActivity(intent);
                    break;
                case R.id.saved:
                    Intent intent1 = new Intent(ProfilePage.this,SaveHome.class);
                    startActivity(intent1);
                    break;
                case R.id.chat:
                    Intent intent2 = new Intent(ProfilePage.this,MessagingActivity.class);
                    startActivity(intent2);
                    break;

            }

            return true;

        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfilePage.this,ConfirmImage.class);
                startActivity(intent);

            }
        });

        myPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this,MyPost.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(ProfilePage.this,StartPages.class);
                startActivity(intent);
            }
        });

        postHomeBt = findViewById(R.id.postHomeBt);
        postHomeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this,PostHome.class);
                startActivity(intent);
            }
        });

        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage.this,PersonalInfo.class);
                startActivity(intent);
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