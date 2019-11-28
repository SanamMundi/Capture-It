package com.example.captureit;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
public class PhotographerRegister extends Fragment {


    private FirebaseAuth mAuth;
    Spinner locationSpinner;
    Spinner experienceSpinner;
    FirebaseFirestore db ;
    private Button pRegister;
    private TextView signInInstead;
    private EditText name, email, pass, cPass;
    Intent intent;

    public PhotographerRegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photographer_register, container, false);

        mAuth = FirebaseAuth.getInstance();
        locationSpinner = v.findViewById(R.id.spinnerLocation);
        experienceSpinner = v.findViewById(R.id.spinnerExperience);
        pRegister = v.findViewById(R.id.registerButton);
        signInInstead = v.findViewById(R.id.signInTextview);
        name = v.findViewById(R.id.enteredName);
        email = v.findViewById(R.id.enteredEmail);
        pass = v.findViewById(R.id.enteredPassword);
        cPass = v.findViewById(R.id.enteredCPassword);

        db =  FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.experience, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experienceSpinner.setAdapter(adapter1);








        pRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                MainActivity.addImagesFragment()
//                ;
                final String naam = name.getText().toString();
                final String emails = email.getText().toString();
                final String pas = pass.getText().toString();
                final String confirm = cPass.getText().toString();

                final String locationData = locationSpinner.getSelectedItem().toString();
                final String experienceData = experienceSpinner.getSelectedItem().toString();


                String result = validateData(naam, emails, pas, confirm, locationData, experienceData);

                if(result.equals("Good")){
                    mAuth.createUserWithEmailAndPassword(emails, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();


                                HashMap<String,String> data =  new HashMap<>();
                                data.put("email",user.getEmail());


                                if(!user.getEmail().equals("sanam@gmail.com")) {
                                    Toast.makeText(getContext(), "already logged in ", Toast.LENGTH_LONG).show();
                                    intent = new Intent(getContext(),HomeActivity.class);
                                    data.put("role", "photographer");
                                }
                                else {
                                    intent = new Intent(getContext(), BaseActivity.class);
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

        signInInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.loginFragment();
            }
        });


        return v;
    }

    public String validateData(String email, String name, String pass, String confirmPassword,
                             String location, String experience){

        if(email.equals("")||name.equals("")||pass.equals("")||confirmPassword.equals("")||location.equals("")||experience.equals("")){
            return "no empty field/s";
        }else{
            if(!(pass.equals(confirmPassword))){
                return "Passwords do not match";
            }
            else{
                return "Good";
            }
        }

    }
}
