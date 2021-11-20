package com.example.weread;

import static java.lang.System.load;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterRecyclerViewHome extends RecyclerView.Adapter<AdapterRecyclerViewHome.ViewHolderHome> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Book> bookList;

    public AdapterRecyclerViewHome(Context mContext, ArrayList<Book> bookList) {
        this.mContext = mContext;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public AdapterRecyclerViewHome.ViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_home,parent,false);
//        AdapterRecyclerViewHome.ViewHolderHome ViewHolderHome = new ViewHolderHome(view);
        return new ViewHolderHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerViewHome.ViewHolderHome holder, int position) {
        //textview
        holder.textView.setText(bookList.get(position).getTitle());

        //imageview : glide library
        //glide.with(this).load(link).into(imageview);
        Glide.with(mContext)
            .load(bookList.get(position).getImageURL())
            .into(holder.imageView);
//        holder.imageView.setImageResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolderHome extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolderHome(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_book);
            textView = itemView.findViewById(R.id.tv_title);
        }
    }

}
