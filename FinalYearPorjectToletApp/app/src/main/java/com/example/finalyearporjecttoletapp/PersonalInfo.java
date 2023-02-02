package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.finalyearporjecttoletapp.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalInfo extends AppCompatActivity {

    TextView nameTxt,emailTxt,phoneTxt;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth;
    String userId;
    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        nameTxt = findViewById(R.id.nameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        profile_image = findViewById(R.id.profile_image);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();


        reference = FirebaseDatabase.getInstance().getReference("User").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                nameTxt.setText(userModel.getName());
                emailTxt.setText(userModel.getEmail());
                phoneTxt.setText(userModel.getPhoneNumber());


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







    }
}