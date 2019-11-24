package com.example.captureit;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {


    String[] nav = {"History", "My Requests", "Logout"};
    FirebaseAuth auth =  FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    Fragment fragment;
    TextView tv;
    String userRole="";
    View v;



    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_more, container, false);



        user = auth.getCurrentUser();

        DocumentReference docRef = db.collection("Users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {


                    List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                    for(int i=0; i<nav.length; i++){
                        HashMap<String, String> hm = new HashMap<>();
                        if(!nav[i].equals("")){
                            hm.put("list")
                        }
                    }


                }else{

                }
                }
        });








        return v;
    }

    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(),BaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
