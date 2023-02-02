package com.example.finalyearporjecttoletapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearporjecttoletapp.Model.ChatModel;
import com.example.finalyearporjecttoletapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<ChatModel> list;
    Context context;

    int SENDER_VIEW_TYPE = 1;
    int RECEVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<ChatModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_recever,parent,false);
            return new ReceverViewHolder(view);
        }

    }


    @Override
    public int getItemViewType(int position) {

        if(list.get(position).getUserId().equals(FirebaseAuth.getInstance().getUid())){

            return SENDER_VIEW_TYPE;

        }
        else {
            return RECEVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChatModel chatModel = list.get(position);
        if(holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMessage.setText(chatModel.getMessage());

        }
        else {
            ((ReceverViewHolder)holder).receverMessage.setText(chatModel.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReceverViewHolder extends RecyclerView.ViewHolder{

        TextView receverMessage, receverTime;

        public ReceverViewHolder(@NonNull View itemView) {
            super(itemView);
            receverMessage = itemView.findViewById(R.id.receverText);
            receverTime = itemView.findViewById(R.id.receverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMessage, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessage = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);

        }
    }


}
