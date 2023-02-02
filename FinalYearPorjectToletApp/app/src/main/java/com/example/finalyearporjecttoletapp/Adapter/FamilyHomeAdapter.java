package com.example.finalyearporjecttoletapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearporjecttoletapp.Model.HomeModel;
import com.example.finalyearporjecttoletapp.Model.UserModel;
import com.example.finalyearporjecttoletapp.PropertyDetails;
import com.example.finalyearporjecttoletapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FamilyHomeAdapter extends RecyclerView.Adapter<FamilyHomeAdapter.viewholder>{

    ArrayList<HomeModel> list;
    Context context;
    String key;

    public FamilyHomeAdapter(ArrayList<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_recyclerview,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        HomeModel homeModel = list.get(position);

        holder.BedRoom.setText(homeModel.getBedRoom());
        holder.BathRoom.setText(homeModel.getBathRoom());
        holder.kitchenRoom.setText(homeModel.getKitchenRoom());
        holder.price.setText(homeModel.getPrice());
        holder.location.setText(homeModel.getLocation());
        holder.PropertyType.setText(homeModel.getPropertyType());
        holder.division.setText(homeModel.getDivision());
        holder.district.setText(homeModel.getDistrict());
        holder.area.setText(homeModel.getArea());

        String imageUrl = null;
        imageUrl = homeModel.getImage();
        Picasso.get().load(imageUrl).into(holder.image);

        key = homeModel.getKey();

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentUserId = user.getUid();
                FirebaseDatabase.getInstance().getReference("SaveHome").child(homeModel.getKey().toString()+currentUserId).setValue(homeModel);
                Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();

            }
        });


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),PropertyDetails.class);

                Bundle bundle = new Bundle();
                bundle.putString("Key", homeModel.getKey());
                bundle.putString("UserId",homeModel.getUserId());
                intent.putExtras(bundle);

                view.getContext().startActivity(intent);

                Toast.makeText(context, "click done", Toast.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    public int getItemCount() {
        return list.size();

    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView image,save;
        TextView location,BedRoom,BathRoom,kitchenRoom,price,PropertyType,division,district,area;
       // LinearLayout layout;
        LinearLayout layout;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.homeImage);
            location = itemView.findViewById(R.id.location);
            BedRoom = itemView.findViewById(R.id.roomAmount);
            BathRoom = itemView.findViewById(R.id.bathRoomAmount);
            kitchenRoom = itemView.findViewById(R.id.kitchenAmount);
            price = itemView.findViewById(R.id.amount);
            PropertyType = itemView.findViewById(R.id.type);
            save = itemView.findViewById(R.id.saveHome);
            division = itemView.findViewById(R.id.division);
            district = itemView.findViewById(R.id.district);
            area = itemView.findViewById(R.id.aria);
            //layout = itemView.findViewById(R.id.layout);
            layout = itemView.findViewById(R.id.homelayout);

        }
    }

}
