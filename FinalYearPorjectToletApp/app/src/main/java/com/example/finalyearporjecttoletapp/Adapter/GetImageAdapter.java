package com.example.finalyearporjecttoletapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearporjecttoletapp.Model.GetImageModel;
import com.example.finalyearporjecttoletapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GetImageAdapter extends RecyclerView.Adapter<GetImageAdapter.MyViewHolder> {

    Context context;
    ArrayList<GetImageModel> getImageModelArrayList;

    public GetImageAdapter(Context context, ArrayList<GetImageModel> getImageModelArrayList) {
        this.context = context;
        this.getImageModelArrayList = getImageModelArrayList;
    }

    @NonNull
    @Override
    public GetImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.get_image,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetImageAdapter.MyViewHolder holder, int position) {

        GetImageModel getImageModel = getImageModelArrayList.get(position);
        String imageUrl = null;
        imageUrl = getImageModel.getImagelink();
        Picasso.get().load(imageUrl).into(holder.getImages);


    }

    @Override
    public int getItemCount() {
        return getImageModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView getImages;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            getImages = itemView.findViewById(R.id.getimage_view);


        }
    }
}
