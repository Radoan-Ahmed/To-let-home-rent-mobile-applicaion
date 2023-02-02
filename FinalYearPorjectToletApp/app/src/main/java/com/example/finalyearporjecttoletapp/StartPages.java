package com.example.finalyearporjecttoletapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartPages extends AppCompatActivity implements View.OnClickListener{

    Button loginButton, signupButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_pages);
        getSupportActionBar().hide();

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        auth = FirebaseAuth.getInstance();


        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(StartPages.this,HomePage.class);
            startActivity(intent);
            finishAffinity();
        }



        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.loginButton:
                Intent intent = new Intent(StartPages.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.signupButton:
                Intent intent1 = new Intent(StartPages.this,SignupActivity.class);
                startActivity(intent1);
                break;
        }

    }
}