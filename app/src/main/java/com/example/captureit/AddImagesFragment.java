package com.example.captureit;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.admin.v1beta1.Progress;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddImagesFragment extends Fragment {


    private static final int PICK_IMAGE_REQUEST = 1;
//    private static final int RESULT_OK = -1;
    private Button buttonUpload;
    private Button buttonChoose;
    private ProgressBar pBar;
    private ImageView img;

    private StorageReference mstorageRef;
//    private DatabaseReference mdatabaseRef;


    private Uri mImageUri;

    public AddImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View v = inflater.inflate(R.layout.fragment_add_images, container, false);




        buttonChoose = v.findViewById(R.id.choose);
        buttonUpload = v.findViewById(R.id.upload);
        img = v.findViewById(R.id.imageShow);
        pBar = v.findViewById(R.id.progressBar);

        mstorageRef = FirebaseStorage.getInstance().getReference("uploads");
//        mdatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");



//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        mdatabaseRef = db.getReference("uploads");


        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });




        return v;
    }


    private void openFileChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() !=null){
            mImageUri = data.getData();
            Glide.with(getContext()).load(mImageUri).into(img);
        }
    }

    private String getFileExtension(Uri uri){

        ContentResolver cr = getContext().getContentResolver();

        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    private void uploadFile(){
        if(mImageUri!=null){
            StorageReference fileReference = mstorageRef.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pBar.setProgress(0);
                        }
                    }, 2000);

                    Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();

                    Upload upload;
                    upload = new Upload("fasdfasdf", taskSnapshot.getUploadSessionUri().toString());
//                    String uploadID = mdatabaseRef.push().getKey();
//                    mdatabaseRef.child(uploadID).setValue(upload);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Can't upload file", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            pBar.setProgress((int)progress);
                        }
                    });
        }else{
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
