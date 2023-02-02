package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    EditText email;
    Button sendButton;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.email);
        sendButton = findViewById(R.id.sendButton);
        auth = FirebaseAuth.getInstance();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forget();

            }
        });



    }

    private void forget() {

        String mail = email.getText().toString().trim();
        if(!mail.matches(emailPattern)){
            email.setError("Enter Correct Email");
            email.requestFocus();
        }

        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Intent intent = new Intent(ForgetPassword.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(ForgetPassword.this,"Please Check your mail address",Toast.LENGTH_LONG).show();
                    

                }
                else{
                    Toast.makeText(ForgetPassword.this,"Enter correct mail",Toast.LENGTH_LONG).show();

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(ForgetPassword.this,e.getMessage(),Toast.LENGTH_LONG).show();


            }
        });

    }
}