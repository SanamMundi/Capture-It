package com.example.captureit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    String userRole="";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());
        db = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        fragmentManager = getSupportFragmentManager();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        Log.d("useremail", currentUser.getEmail());

        if(currentUser!=null){
            Log.d("current user", currentUser.toString());
            Log.d("dddddddd", "ddddddddddd");
            DocumentReference docRef = db.collection("Users").document(currentUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }else{
            loginFragment();
        }
    }


    public static void loginFragment()
    {
        fragmentManager.beginTransaction().replace(R.id.frameContainer,new LoginFragment()).commit();
    }

    public static void signUpFragment()
    {
        fragmentManager.beginTransaction().replace(R.id.frameContainer,new SignUpFragment()).commit();
    }
}
