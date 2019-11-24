package com.example.captureit;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    private FirebaseAuth mAuth;
    //    private DBConnection dbh;
    private EditText eTEmail, eTPassword;
    private TextView alreadyAccount;
    FirebaseFirestore db ;
    String userRole;
    Intent intent;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mAuth = FirebaseAuth.getInstance();
        Button signUp = v.findViewById(R.id.buttonSignup);
        eTEmail = v.findViewById(R.id.eTSignUpEmail);
        eTPassword = v.findViewById(R.id.eTSignUpPassword);
        alreadyAccount = v.findViewById(R.id.accountexists);

        db =  FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
//        db.setFirestoreSettings(settings);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = eTEmail.getText().toString();
                String password = eTPassword.getText().toString();


                if(email.equals("")||password.equals("")){
                    Toast.makeText(getActivity(), "Field/s empty", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();


                                HashMap<String,String> data =  new HashMap<>();
                                data.put("email",user.getEmail());


                                if(!user.getEmail().equals("jasmine@gmail.com")) {
                                    Toast.makeText(getContext(), "already logged in ", Toast.LENGTH_LONG).show();
                                    intent = new Intent(getContext(),HomeActivity.class);

                                    data.put("role", "user");
                                }
                                else {


                                    intent = new Intent(getContext(), LoginActivity.class);
                                    data.put("role", "admin");
                                }

                                db.collection("Users").document(user.getUid()).set(data);


                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.loginFragment();
            }
        });


        return v;
    }

}
