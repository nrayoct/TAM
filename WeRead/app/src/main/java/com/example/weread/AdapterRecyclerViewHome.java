package com.example.weread;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRecyclerViewHome extends RecyclerView.Adapter<AdapterRecyclerViewHome.ViewHolderHome> {

    int []image;
    public AdapterRecyclerViewHome(int[] image){
        this.image = image;
    }

    public class ViewHolderHome extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolderHome(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_book);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerViewHome.ViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_home,parent,false);
        AdapterRecyclerViewHome.ViewHolderHome ViewHolderHome = new ViewHolderHome(view);
        return ViewHolderHome;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerViewHome.ViewHolderHome holder, int position) {
        holder.imageView.setImageResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return image.length;
    }
}
