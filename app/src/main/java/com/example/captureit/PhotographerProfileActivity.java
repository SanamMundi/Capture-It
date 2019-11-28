package com.example.captureit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class PhotographerProfileActivity extends AppCompatActivity {

    private Button edit, editPhoto, logout;
    private EditText etName, etPrice;
    private Spinner locationSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_profile);

        locationSpinner = findViewById(R.id.spinLoc);
        edit = findViewById(R.id.editButton);
        editPhoto = findViewById(R.id.changePhotos);
        logout = findViewById(R.id.logoutButton);
        etName = findViewById(R.id.nameEt);
        etPrice = findViewById(R.id.priceEt);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = etPrice.getText().toString();
                String name = etName.getText().toString();
                String loca = locationSpinner.getSelectedItem().toString();


            }
        });



    }


    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
