package com.example.captureit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;
//    private FirebaseAuth firebaseAuth;
//    Firebase f = new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        fragmentManager = getSupportFragmentManager();
//        f.addData();
    }

    public static void loginFragment(){

    }

    public static void signUpFragment(){

    }


}
