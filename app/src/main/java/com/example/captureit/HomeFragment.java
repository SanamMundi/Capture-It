package com.example.captureit;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.grpc.Context;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    Context ctx;
    RecyclerView mRecyclerView;
    MyAdapter myAdapter;

    ArrayList<Photographer> arr = new ArrayList<>();;

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

        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Photographer p = new Photographer();
                                Log.d("dataaaaaaaa", document.getId() + " => " + document.getData());
                                p.setId(document.getId());
                                p.setName(document.get("name").toString());
                                p.setEmail(document.get("email").toString());
                                p.setExperience(document.get("experience").toString());
                                p.setDescription(document.get("description").toString());
                                p.setLocationName(document.get("location").toString());
                                p.setPrice(document.get("price").toString());
                                arr.add(p);
                            }
                            Log.d("sizeeeee", arr.size() +"");
                            for(Photographer p: arr){
                                Log.d("aaaaaaaaaa", p.toString());
                            }


                            myAdapter = new MyAdapter(getContext(), getMyList(arr));
                            mRecyclerView.setAdapter(myAdapter);

                        } else {
                            Log.w("eeeeeeeeee", "Error getting documents.", task.getException());
                        }
                    }
                });




        return v;
    }

    private ArrayList<Model> getMyList(ArrayList<Photographer> arr){

        ArrayList<Model> models = new ArrayList<>();

        for(int i=0; i<arr.size(); i++){
            Model m = new Model();
            m.setDescription(arr.get(i).getLocationName());
            m.setTitle(arr.get(i).getName());
            m.setPrice(arr.get(i).getPrice());
            models.add(m);
        }
        return models;
    }
}
