package com.example.captureit;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    RecyclerView mRecyclerView;
    MyAdapter myAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myAdapter = new MyAdapter(getContext(), getMyList());
        mRecyclerView.setAdapter(myAdapter);

        return v;
    }

    private ArrayList<Model> getMyList(){

        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setTitle("News Feed");
        m.setDescription("this is something");
        m.setImg(R.drawable.button_default);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed");
        m.setDescription("this is something");
        m.setImg(R.drawable.button_default);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed");
        m.setDescription("this is something");
        m.setImg(R.drawable.common_google_signin_btn_text_dark_focused);
        models.add(m);

        return models;
    }
}
