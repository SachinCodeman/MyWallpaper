package com.example.mywallpaper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mywallpaper.R;
import com.example.mywallpaper.model.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final ArrayList<CategoryModel> categoryModelList;
    private final Context context;
    private final CategoryClickInterface categoryClickInterface;

    public CategoryAdapter(ArrayList<CategoryModel> categoryModelList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryModelList = categoryModelList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        CategoryModel categoryModel = categoryModelList.get(position);
        holder.categoryTitle.setText(categoryModel.getCategoryTitleUrl());
        holder.categoryImage.setImageResource(categoryModel.getCategoryImageUrl());
      //  Glide.with(context).load("https://unsplash.com/photos/s5kTY-Ve1c0").into(holder.categoryImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView categoryTitle ;
        private final ImageView categoryImage ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.category_cardTextview);
            categoryImage = itemView.findViewById(R.id.category_cardImageview);

        }
    }

    public interface CategoryClickInterface {
        void onCategoryClick(int position);
    }
}
