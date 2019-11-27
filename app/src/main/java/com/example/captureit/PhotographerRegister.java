package com.example.captureit;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotographerRegister extends Fragment {


    Spinner locationSpinner;
    Spinner experienceSpinner;

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.experience, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experienceSpinner.setAdapter(adapter1);
//        String result = validateData();


        String locationData = locationSpinner.getSelectedItem().toString();
        String experienceData = experienceSpinner.getSelectedItem().toString();

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
