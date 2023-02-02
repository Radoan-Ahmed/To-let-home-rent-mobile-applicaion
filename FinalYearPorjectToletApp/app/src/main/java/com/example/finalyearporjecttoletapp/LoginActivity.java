package com.example.finalyearporjecttoletapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearporjecttoletapp.Model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth auth;
    DatabaseReference reference;
    EditText loginUserName,loginPassword;
    TextView forgetPassword;
    Button loginbt,googleSignInBt;
    ProgressDialog progressDialog;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        loginUserName = findViewById(R.id.loginUserName);
        loginPassword = findViewById(R.id.loginPassword);
        loginbt = findViewById(R.id.loginbt);
        forgetPassword = findViewById(R.id.forgetPassword);
        googleSignInBt = findViewById(R.id.googleSignInBt);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);




        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("We're are login your account");

        loginbt.setOnClickListener(this);
        googleSignInBt.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);

        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this,HomePage.class);
            startActivity(intent);
            finishAffinity();
            System.exit(0);
        }



    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.googleSignInBt:
                google();
                break;
            case R.id.loginbt:
                login();
                break;
            case R.id.forgetPassword:
                forgetPass();
                break;
        }

    }

    private void forgetPass() {

        Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
        startActivity(intent);


    }

    private void google() {

        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if(requestCode == 100){
             Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
             try{
                 task.getResult(ApiException.class);
                 HomeActivity();

             }catch (ApiException e){
                 Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
             }
         }

    }

    private void HomeActivity() {
       GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
       if(account!=null){
           String name = account.getDisplayName();
           String mail = account.getEmail();
           Uri picture = account.getPhotoUrl();
           String pic = picture.toString();
           UserModel userModel = new UserModel(name,mail,pic);
           reference = FirebaseDatabase.getInstance().getReference("User");
           String id = reference.push().getKey();
           reference.child(id).setValue(userModel);
           Toast.makeText(LoginActivity.this,"Registration Successfull",Toast.LENGTH_LONG).show();
           Intent intent = new Intent(LoginActivity.this,HomePage.class);
           startActivity(intent);
       }
    }

    private void login() {


        String email = loginUserName.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        progressDialog.show();

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login Successfull",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this,HomePage.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_LONG).show();

                }
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