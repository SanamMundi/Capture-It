package com.example.captureit;


import android.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotographerFragment extends Fragment {


    TextView photoTitleTv, photoDescTv;
    ImageView photoImage;

    public PhotographerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photographer, container, false);


        photoTitleTv = v.findViewById(R.id.titlePhotographer);
        photoDescTv = v.findViewById(R.id.descriptionPhotographer);
        photoImage = v.findViewById(R.id.imagePhotographer);


        return v;

    }

}
