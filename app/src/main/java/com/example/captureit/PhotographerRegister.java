package com.example.captureit;


import android.os.Bundle;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotographerRegister extends Fragment {


    Spinner locationSpinner;
    Spinner experienceSpinner;
    private Button pRegister;
    private TextView signInInstead;
    private EditText name, email, pass, cPass;

    public PhotographerRegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photographer_register, container, false);

        locationSpinner = v.findViewById(R.id.spinnerLocation);
        experienceSpinner = v.findViewById(R.id.spinnerExperience);
        pRegister = v.findViewById(R.id.registerButton);
        signInInstead = v.findViewById(R.id.signInTextview);
        name = v.findViewById(R.id.enteredName);
        email = v.findViewById(R.id.enteredEmail);
        pass = v.findViewById(R.id.enteredPassword);
        cPass = v.findViewById(R.id.enteredCPassword);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.experience, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experienceSpinner.setAdapter(adapter1);


        final String naam = name.getText().toString();
        final String emails = email.getText().toString();
        final String pas = pass.getText().toString();
        final String confirm = cPass.getText().toString();


        String locationData = locationSpinner.getSelectedItem().toString();
        String experienceData = experienceSpinner.getSelectedItem().toString();


        pRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                MainActivity.addImagesFragment();
//                validateData();

                Log.d("hello", "hello");
                Log.d("jjjjjjjjjjjjjjjjjj",naam + emails + pas + confirm );
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
