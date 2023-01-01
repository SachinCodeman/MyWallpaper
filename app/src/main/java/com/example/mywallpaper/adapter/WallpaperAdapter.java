package com.example.mywallpaper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mywallpaper.R;
import com.example.mywallpaper.WallpaperActivity;

import java.util.ArrayList;

public class WallpaperAdapter  extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder>{

    private ArrayList<String> wallpaperURLList;
    private Context context;

    public WallpaperAdapter(ArrayList<String> wallpaperURLList, Context context) {
        this.wallpaperURLList = wallpaperURLList;
        this.context = context;
    }

    @NonNull
    @Override
    public WallpaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_item,parent,false);
        return new WallpaperAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(wallpaperURLList.get(position)).into(holder.wallpaperImage);
        String link = wallpaperURLList.get(position).toString();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WallpaperActivity.class);
                intent.putExtra("imgUrl",wallpaperURLList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperURLList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView wallpaperImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperImage = itemView.findViewById(R.id.wallpaper_ImageView);

        }
    }
}
