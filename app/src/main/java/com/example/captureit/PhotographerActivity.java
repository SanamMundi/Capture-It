package com.example.captureit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PhotographerActivity extends AppCompatActivity {

    TextView mTitleTv, mDescTv;
    ImageView mImageIv;

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference mdatabaseRef = database.getReference("uploads/");
    private List<Upload> mUploads;


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



        recyclerView = findViewById(R.id.imagesRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads  = new ArrayList<>();

//        mdatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mdatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Upload upload = dataSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                imageAdapter = new ImageAdapter(PhotographerActivity.this, mUploads);
                recyclerView.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PhotographerActivity.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });





    }
}
