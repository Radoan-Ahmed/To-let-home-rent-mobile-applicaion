package com.example.finalyearporjecttoletapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearporjecttoletapp.Model.FamilyHomeModel;
import com.example.finalyearporjecttoletapp.Model.PostHomeModel;
import com.example.finalyearporjecttoletapp.MyPost;
import com.example.finalyearporjecttoletapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.viewholder>{

    ArrayList<PostHomeModel> list;
    Context context;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;

    String userId;
    public MyPostAdapter(ArrayList<PostHomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mypost_recyclerview,parent,false);

        database = FirebaseDatabase.getInstance();
      //  reference = database.getReference().child("HomeInfo");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        PostHomeModel homeModel = list.get(position);

        holder.BedRoom.setText(homeModel.getBedRoom());
        holder.BathRoom.setText(homeModel.getBathRoom());
        holder.kitchenRoom.setText(homeModel.getKitchenRoom());
        holder.price.setText(homeModel.getPrice());
        holder.location.setText(homeModel.getLocation());
        holder.PropertyType.setText(homeModel.getPropertyType());

        String imageUrl = null;
        imageUrl = homeModel.getImage();
        Picasso.get().load(imageUrl).into(holder.image);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Are you sure");
                builder.setMessage("Delete data can not be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("HomeInfo").child(homeModel.getKey()).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("Location").child(homeModel.getKey()).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("ImageName").child(homeModel.getKey()).removeValue();

                        Toast.makeText(context, "delete done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), MyPost.class);
                        view.getContext().startActivity(intent);
                       // list.remove(position);
                        //notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        ImageView image,delete;
        TextView location,BedRoom,BathRoom,kitchenRoom,price,PropertyType;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.myPosthomeImage);
            location = itemView.findViewById(R.id.myPostlocation);
            BedRoom = itemView.findViewById(R.id.myPostroomAmount);
            BathRoom = itemView.findViewById(R.id.myPostbathRoomAmount);
            kitchenRoom = itemView.findViewById(R.id.myPostkitchenAmount);
            price = itemView.findViewById(R.id.myPostamount);
            PropertyType = itemView.findViewById(R.id.myPosttype);
            delete = itemView.findViewById(R.id.delete);

        }
    }

}
