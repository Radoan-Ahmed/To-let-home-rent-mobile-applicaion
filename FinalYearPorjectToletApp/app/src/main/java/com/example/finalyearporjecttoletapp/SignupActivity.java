package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalyearporjecttoletapp.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText userNametextView,useEmailTextView,userPhoneTextView,userPassworTextView,confirmPasswordTextView;
    Button createAccountButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        userNametextView = findViewById(R.id.userNametextView);
        useEmailTextView = findViewById(R.id.useEmailTextView);
        userPhoneTextView = findViewById(R.id.userPhoneTextView);
        userPassworTextView = findViewById(R.id.userPassworTextView);
        confirmPasswordTextView = findViewById(R.id.confirmPasswordTextView);
        createAccountButton = findViewById(R.id.createAccountButton);

        auth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account");


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }


    private void signIn() {

        String email = useEmailTextView.getText().toString().trim();
        String password = userPassworTextView.getText().toString().trim();
        String confirmPassword = confirmPasswordTextView.getText().toString().trim();
        String name = userNametextView.getText().toString().trim();
        String phonenumber = userPhoneTextView.getText().toString().trim();
        String image = "default";


        if(!email.matches(emailPattern)){
            useEmailTextView.setError("Enter Correct Email");
            userNametextView.requestFocus();

        }

        if(password.isEmpty() || password.length()<6){
            userPassworTextView.setError("Password length sould be bigger then 6");
            userPassworTextView.requestFocus();
        }
        if(!password.equals(confirmPassword)){
            confirmPasswordTextView.setError("Password not match");
            confirmPasswordTextView.requestFocus();
        }

        progressDialog.show();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){

                    String id = task.getResult().getUser().getUid();
                    UserModel userModel = new UserModel(name,email,phonenumber,password,image,id);
                    reference = FirebaseDatabase.getInstance().getReference("User");
                    reference.child(id).setValue(userModel);

                    //database.getReference("User").child(id).setValue(userModel);

                    Toast.makeText(SignupActivity.this,"Registration Successfull",Toast.LENGTH_LONG).show();

                    loginActivity();

                }
                else{
                    Toast.makeText(SignupActivity.this,"Registration Failed",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    private void loginActivity() {
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


}