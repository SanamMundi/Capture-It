package com.example.captureit;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

                    userRole = document.get("role")+"";

                    List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
                    for(int i=0; i<nav.length; i++){
                        HashMap<String, String> hm = new HashMap<>();
                        if(!nav[i].equals("")) {
                            hm.put("listview_title", nav[i]);
                        }
                            aList.add(hm);
                    }

                    String[] from = {"listview_title"};
                    int[] to = {R.id.text_row};

                    SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.listview_row, from, to);
                    ListView androidListView = (ListView) v.findViewById(R.id.listView);
                    androidListView.setAdapter(simpleAdapter);

                    String uDetails = user.getEmail();
                    tv = (TextView)v.findViewById(R.id.user);
                    tv.setText("Hey! " + uDetails);



                    androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            if(nav[position].equals("History"))
                            {
                                fragment = new HomeFragment();
                                loadFragment(fragment);
                            }
                            if(nav[position].equals("My Request"))
                            {
                                Bundle b = new Bundle();
                                b.putString("city","Vancouver");
                                fragment = new MapFragment();
                                fragment.setArguments(b);
                                loadFragment(fragment);
                            }
                            if(nav[position].equals("Logout"))
                            {
                                logout();

                            }
                        }
                    });
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

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
