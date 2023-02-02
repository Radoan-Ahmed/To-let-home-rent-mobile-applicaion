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
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ConfirmImage extends AppCompatActivity {

    ImageView profileImg,editBt;
    Button confirm_image;
    FirebaseStorage storage;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    String userId;
    FirebaseUser user;
    Uri uri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_image);
        profileImg = findViewById(R.id.profile_img);
        confirm_image = findViewById(R.id.confirm_image);
        editBt = findViewById(R.id.editBt);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("User").child(userId);
        storage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(ConfirmImage.this);
        progressDialog.setTitle("Update Profile Picture");
        progressDialog.setMessage("Your profile picture in updating");

        confirm_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueSubmit();
            }
        });
        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ConfirmImage.this)
                        //.crop()	    			//Crop image(Optional), Check Customization for more option
                        // .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        // .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri = data.getData();
        String Image = uri.getPath().toString();
        profileImg.setImageURI(uri);

    }




    private void ValueSubmit() {

        progressDialog.show();

        StorageReference filepath = storage.getReference().child("imagePost").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {


                        //DatabaseReference newPost = reference.push();
                        reference.child("image").setValue(task.getResult().toString());


                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Intent intent = new Intent(ConfirmImage.this,HomePage.class);
                            startActivity(intent);
                            Toast.makeText(ConfirmImage.this,"Post submit successfull",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(ConfirmImage.this,"can not submit post",Toast.LENGTH_LONG).show();

                        }

                    }
                });


            }
        });



    }
}