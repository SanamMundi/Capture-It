package com.example.captureit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PhotographerActivity extends AppCompatActivity {

    TextView mTitleTv, mDescTv;
    ImageView mImageIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer);

        ActionBar actionBar = getSupportActionBar();

        mTitleTv = findViewById(R.id.titlePhotographer);
        mDescTv = findViewById(R.id.descriptionPhotographer);
        mImageIv = findViewById(R.id.imagePhotographer);


        Intent intent = getIntent();

        String mTitle = intent.getStringExtra("iTitle");
        String mDescription = intent.getStringExtra("iDesc");
        String mImg = intent.getStringExtra("iImage");

        mTitleTv.setText(mTitle);
        mDescTv.setText(mDescription);

        Glide.with(getApplicationContext()).load(mImg).into(mImageIv);



    }
}
