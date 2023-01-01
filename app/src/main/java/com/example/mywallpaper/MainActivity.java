package com.example.mywallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mywallpaper.adapter.CategoryAdapter;
import com.example.mywallpaper.adapter.WallpaperAdapter;
import com.example.mywallpaper.databinding.ActivityMainBinding;
import com.example.mywallpaper.model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {

    private ActivityMainBinding binding ;
    private ArrayList<CategoryModel> catagoryURLList ;
    private ArrayList<String> wallpaperURLList ;
    private CategoryAdapter categoryAdapter;
    private WallpaperAdapter wallpaperAdapter;

    // Api Key    563492ad6f91700001000001845c6cae10594f5c940033d44ad2eb17


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        catagoryURLList = new ArrayList<>();
        wallpaperURLList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.category.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(catagoryURLList,this, this);
        binding.category.setAdapter(categoryAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        binding.wallpapers.setLayoutManager(gridLayoutManager);
        wallpaperAdapter = new WallpaperAdapter(wallpaperURLList,this);
        binding.wallpapers.setAdapter(wallpaperAdapter);


        getCategories();
        getWallpapers();


        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = binding.editSearch.getText().toString();
                if(query.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please Enter The Search Query",Toast.LENGTH_SHORT).show();
                }
                else {
                    getWallpapersByCategories(query);
                }
            }
        });

    }

    public void getWallpapersByCategories(String category){
        wallpaperURLList.clear();
        binding.progressBar.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/search?query="+category+"&per_page=30&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONObject response) {
                JSONArray photoArray = null;
                binding.progressBar.setVisibility(View.GONE);
                try {
                    photoArray = response.getJSONArray("photos");
                    for (int i=0;i<photoArray.length();i++){

                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imageUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperURLList.add(imageUrl);

                    }
                    wallpaperAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                        e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Failed to load Wallpapers",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String>headers = new HashMap<>();
                headers.put("Authorization"," 563492ad6f91700001000001845c6cae10594f5c940033d44ad2eb17");
                return headers;

            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void getWallpapers() {
        wallpaperURLList.clear();
        binding.progressBar.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=30&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONObject response) {
                binding.progressBar.setVisibility(View.GONE);
                JSONArray photoArray = null;
                try {
                    photoArray = response.getJSONArray("photos");
                    for (int i=0;i<photoArray.length();i++){

                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imageUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperURLList.add(imageUrl);

                    }
                    wallpaperAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Failed to load Wallpapers",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String>headers = new HashMap<>();
                headers.put("Authorization"," 563492ad6f91700001000001845c6cae10594f5c940033d44ad2eb17");
                return headers;

            }
        };

        requestQueue.add(jsonObjectRequest);
    }


    @SuppressLint("NotifyDataSetChanged")
    private void getCategories() {
        catagoryURLList.add(new CategoryModel("Anime",R.drawable.anime_jinwoo));
        catagoryURLList.add(new CategoryModel("Marvel",R.drawable.marvel));
        catagoryURLList.add(new CategoryModel("Car",R.drawable.car));
        catagoryURLList.add(new CategoryModel("Fashion",R.drawable.fashion));
        catagoryURLList.add(new CategoryModel("Programming",R.drawable.programming));
        catagoryURLList.add(new CategoryModel("GYM",R.drawable.gym));
        catagoryURLList.add(new CategoryModel("Art",R.drawable.art));
        categoryAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCategoryClick(int position) {

        String category = catagoryURLList.get(position).getCategoryTitleUrl();
        Toast.makeText(this,category,Toast.LENGTH_SHORT).show();
        getWallpapersByCategories(category);
    }
}


