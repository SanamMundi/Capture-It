package com.example.captureit;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private FirebaseAuth mAuth;
    private EditText eTEmail,eTPassword;
    private TextView createAccount;
    FirebaseFirestore db;
    int RC_SIGN_IN;
    String userRole;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        eTEmail = v.findViewById(R.id.editText);
        eTPassword =v.findViewById(R.id.editText2);
        createAccount = v.findViewById(R.id.textView6);

        Button signIn = (Button)v.findViewById(R.id.signInButton);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = eTEmail.getText().toString();
                String password = eTPassword.getText().toString();

                if(email.equals("")||password.equals("")){
                    Toast.makeText(getActivity(), "Field/s empty", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        FirebaseUser user = mAuth.getCurrentUser();
                                        DocumentReference docRef =  db.collection("Users").document(user.getUid());
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    userRole=document.get("role")+"";
                                                    if(userRole.equals("admin")){
                                                        Log.d("hello", "Hhhhhhhhhhhhhhhhhhhhhhhhhhh");
                                                        startActivity(new Intent(getContext(),BaseActivity.class));}
                                                    else{
                                                    Log.d("already logged in", "dddddddddddddddddd");
                                                        startActivity(new Intent(getContext(), BaseActivity.class));}

                                                } else {
                                                    Log.d("signin", "No such document");
                                                }
                                            }
                                        });

                                    } else {
                                        Toast.makeText(getContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }
            }
        });


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.signUpFragment();
            }
        });

        return v;
    }

}
