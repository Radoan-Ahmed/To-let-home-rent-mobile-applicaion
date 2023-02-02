package com.example.finalyearporjecttoletapp.Adapter;

import android.content.Context;
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
import com.example.finalyearporjecttoletapp.Model.HomeModel;
import com.example.finalyearporjecttoletapp.Model.PostHomeModel;
import com.example.finalyearporjecttoletapp.R;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewHolder>{

    ArrayList<PostHomeModel> familyHomeModels;
    Context context;

    public HomeAdapter(ArrayList<PostHomeModel> familyHomeModels, Context context) {
        this.familyHomeModels = familyHomeModels;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_recyclerview,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        PostHomeModel homeModel = familyHomeModels.get(position);

        holder.BedRoom.setText(homeModel.getBedRoom());
        holder.BathRoom.setText(homeModel.getBathRoom());
        holder.kitchenRoom.setText(homeModel.getKitchenRoom());
        holder.price.setText(homeModel.getPrice());
        holder.location.setText(homeModel.getLocation());
        holder.PropertyType.setText(homeModel.getPropertyType());

        String imageUrl = null;
        imageUrl = homeModel.getImage();
        Picasso.get().load(imageUrl).into(holder.image);

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("SaveHome").child(FirebaseDatabase.getInstance().getReference().getKey()).setValue(homeModel);
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return familyHomeModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView image,save;
        TextView location,BedRoom,BathRoom,kitchenRoom,price,PropertyType;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.homeImage);
            location = itemView.findViewById(R.id.location);
            BedRoom = itemView.findViewById(R.id.roomAmount);
            BathRoom = itemView.findViewById(R.id.bathRoomAmount);
            kitchenRoom = itemView.findViewById(R.id.kitchenAmount);
            price = itemView.findViewById(R.id.amount);
            PropertyType = itemView.findViewById(R.id.type);
            save = itemView.findViewById(R.id.saveHome);

        }
    }

}
