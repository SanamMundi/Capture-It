package com.example.captureit;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mcontext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads){
        mcontext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.items, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Upload uploadCurrent = mUploads.get(position);
        Log.d("iiiiiiiiii", uploadCurrent.getmImageUrl());
        Picasso.with(mcontext).load(uploadCurrent.getmImageUrl()).placeholder(R.drawable.ic_launcher_foreground).
                fit().centerCrop().into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public ImageViewHolder(View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.image_view_upload);
        }

    }
}
