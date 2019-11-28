package com.example.captureit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    FirebaseAuth auth =  FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    ArrayList<Model> models;

    HomeActivity ha = new HomeActivity();
//    MyHolder myHolder;

    public MyAdapter(Context c, ArrayList<Model> models){
        this.c = c;
        this.models = models;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.res, null);

        user = auth.getCurrentUser();
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        holder.mTitle.setText(models.get(position).getTitle());
        holder.mDes.setText(models.get(position).getDescription());
        holder.mPrice.setText(models.get(position).getPrice() + "/day");



        Glide.with(c).load(models.get(position).getImg())
                .into(holder.imageView);
//        holder.imageView.setImageResource(models.get(position).getImg());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                String gTitle = models.get(position).getTitle();
                String gDesc = models.get(position).getDescription();
                String gImg = models.get(position).getImg();
                String gId = models.get(position).getId();



                Intent i = new Intent(c, PhotographerActivity.class);
                i.putExtra("iTitle", gTitle);
                i.putExtra("iDesc", gDesc);
                i.putExtra("iImage", gImg);
                i.putExtra("id", user.getUid());
                i.putExtra("photoId", gId);
                c.startActivity(i);
            }
        });

    }



    @Override
    public int getItemCount() {
        return models.size();
    }
}
