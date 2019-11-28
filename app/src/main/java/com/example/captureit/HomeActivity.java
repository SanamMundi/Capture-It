package com.example.captureit;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        String userRole;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragment = new HomeFragment();
//                    mTextMessage.setText(R.string.title_home);
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.maps:
                    fragment = new MapFragment();
//                    mTextMessage.setText(R.string.title_home);
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_more:
//                    mTextMessage.setText(R.string.title_notifications);
                    fragment = new MoreFragment();
//                    mTextMessage.setText(R.string.title_home);
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DocumentReference docRef =  db.collection("Users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String userRole;
                    userRole=document.get("role")+"";
                    if(userRole.equals("admin")){
                        Log.d("hello", "Hhhhhhhhhhhhhhhhhhhhhhhhhhh");
                        startActivity(new Intent(getApplicationContext(),AdminActivity.class));}
                    else if(userRole.equals("user")){
                        Log.d("already logged in", "dddddddddddddddddd");
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else if(userRole.equals("photographer")){
                        startActivity(new Intent(getApplicationContext(), PhotographerProfileActivity.class));
                    }

                } else {
                    Log.d("signin", "No such document");
                }
            }
        });
//        loadFragment(new HomeFragment());
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),BaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
