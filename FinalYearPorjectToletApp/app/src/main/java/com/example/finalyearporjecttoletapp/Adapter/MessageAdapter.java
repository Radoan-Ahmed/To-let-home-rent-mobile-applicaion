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

import com.example.finalyearporjecttoletapp.ChatActivity;
import com.example.finalyearporjecttoletapp.Model.MessageModel;
import com.example.finalyearporjecttoletapp.PropertyDetails;
import com.example.finalyearporjecttoletapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.viewholder>{

    ArrayList<MessageModel> list;
    Context context;

    public MessageAdapter(ArrayList<MessageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_recyclerview,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        MessageModel messageModel = list.get(position);

        holder.name.setText(messageModel.getName());
        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid()+messageModel.getUserId())
                .orderByChild("time")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                holder.message.setText(dataSnapshot.child("message").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        if(messageModel.getImage().equals("default")){
            holder.image.setImageResource(R.drawable.ic_baseline_person_24);
        }
        else {
            String imageUrl = null;
            imageUrl = messageModel.getImage();
            Picasso.get().load(imageUrl).into(holder.image);
        }



        holder.layout_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);

                Bundle bundle = new Bundle();
               // bundle.putString("Key", homeModel.getKey());
                bundle.putString("UserId",messageModel.getUserId());
                bundle.putString("Name",messageModel.getName());
                bundle.putString("Image",messageModel.getImage());

                intent.putExtras(bundle);

                view.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name,message;
        LinearLayout layout_View;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.messageProfileImage);
            name = itemView.findViewById(R.id.messageUserName);
            message = itemView.findViewById(R.id.lastMessage);
            layout_View = itemView.findViewById(R.id.layout_View);

        }
    }

}
