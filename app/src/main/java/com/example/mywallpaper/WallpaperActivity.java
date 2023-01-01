package com.example.mywallpaper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mywallpaper.databinding.WallpaperRvItemBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {
    private ImageView imageViewWallpaper;
    private Button seWallpaperBtn;
    private String imgURL;
    private WallpaperManager wallpaperManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        imageViewWallpaper = findViewById(R.id.idIVWallpaper);
        seWallpaperBtn = findViewById(R.id.setWallpaperBtn);
        imgURL = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(imgURL).into(imageViewWallpaper);
        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        seWallpaperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(WallpaperActivity.this).asBitmap().load(imgURL).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(WallpaperActivity.this,"Failed to load the wallpaper", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);
                        }catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(WallpaperActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        return false;

                    }
                }).submit();
                FancyToast.makeText(WallpaperActivity.this,"Wallpaper Set to Home Screen",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
            }
        });


    }
}