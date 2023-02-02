package com.example.finalyearporjecttoletapp.Adapter;

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

import com.example.finalyearporjecttoletapp.Model.PostHomeModel;
import com.example.finalyearporjecttoletapp.Model.SaveHomeModel;
import com.example.finalyearporjecttoletapp.MyPost;
import com.example.finalyearporjecttoletapp.R;
import com.example.finalyearporjecttoletapp.SaveHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SaveHomeAdapter extends RecyclerView.Adapter<SaveHomeAdapter.viewholder>{

    ArrayList<SaveHomeModel> list;
    Context context;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    String userId;

    public SaveHomeAdapter(ArrayList<SaveHomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.savehome_recyclerview,parent,false);

        database = FirebaseDatabase.getInstance();
        //  reference = database.getReference().child("HomeInfo");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        SaveHomeModel homeModel = list.get(position);

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
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String currentUserId = user.getUid();
                        FirebaseDatabase.getInstance().getReference().child("SaveHome").child(homeModel.getKey().toString()+currentUserId).removeValue();
                        Toast.makeText(context, "Unsave done", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(view.getContext(), SaveHome.class);
                       // view.getContext().startActivity(intent);

                    }
                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        ImageView image,delete;
        TextView location,BedRoom,BathRoom,kitchenRoom,price,PropertyType;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.SavehomeImage);
            location = itemView.findViewById(R.id.Savehomelocation);
            BedRoom = itemView.findViewById(R.id.SavehomeroomAmount);
            BathRoom = itemView.findViewById(R.id.SavehomebathRoomAmount);
            kitchenRoom = itemView.findViewById(R.id.SavehomekitchenAmount);
            price = itemView.findViewById(R.id.Savehomeamount);
            PropertyType = itemView.findViewById(R.id.Savehometype);
            delete = itemView.findViewById(R.id.saveHome);


        }
    }

}
