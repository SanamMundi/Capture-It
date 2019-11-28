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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddingMoreFragment extends Fragment {


    private FirebaseAuth mAuth;

    private Firebase user;
    FirebaseFirestore db ;

    private CheckBox wedding, funeral, potrait, birthdays, pregnancy;
    private EditText price, description;
    private Button pro;

    ArrayList<String> ser = new ArrayList<>();



    public AddingMoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_adding_more, container, false);

        mAuth = FirebaseAuth.getInstance();

//        final String[] services = {""};

        wedding = v.findViewById(R.id.weddingCheckbox);
        funeral = v.findViewById(R.id.funeralCheckbox);
        potrait = v.findViewById(R.id.potraitCheckbox);
        birthdays = v.findViewById(R.id.birthdayCheckbox);
        pregnancy = v.findViewById(R.id.pregnancyCheckbox);
        description = v.findViewById(R.id.desscriptionEditText);


        price = v.findViewById(R.id.priceEditText);
        pro = v.findViewById(R.id.proButton);

        db =  FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();





        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String des = description.getText().toString();
                final Double pri = Double.parseDouble(price.getText().toString());




                int flag = 0;

                if(des.equals("")||pri.equals("")){
                    Toast.makeText(getContext(), "no empty fields", Toast.LENGTH_SHORT).show();
                }
                else {



                    Bundle b = getArguments();
                    final String name = b.getString("name");
                    final String email = b.getString("email");
                    final String location = b.getString("location");
                    final String experience = b.getString("experience");
                    final String password = b.getString("password");


                    boolean wedCheck = wedding.isChecked();
                    boolean funeralCheck = funeral.isChecked();
                    boolean potraitCheck = potrait.isChecked();
                    boolean birthCheck = birthdays.isChecked();
                    boolean pregCheck = pregnancy.isChecked();


                    boolean[] arr = new boolean[5];
                    arr[0] = wedCheck;
                    arr[1] = funeralCheck;
                    arr[2] = potraitCheck;
                    arr[3] = birthCheck;
                    arr[4] = pregCheck;



                    if(wedCheck==true){
                        ser.add("Weddings");
                    }
                    if(funeralCheck==true){
                        ser.add("Funeral");
                    }
                    if(potraitCheck==true){
                        ser.add("Portrait");
                    }
                    if(birthCheck==true){
                        ser.add("Birthdays");
                    }if(pregCheck==true){
                        ser.add("Pregnancy");
                    }


                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();

                                HashMap<String,String> data =  new HashMap<>();
                                data.put("email",user.getEmail());
                                data.put("name", name);
                                data.put("location", location);
                                data.put("experience", experience);
                                data.put("price", pri.toString());
                                data.put("description", des);
//                                data.put("services", services[0]);


                                if(!user.getEmail().equals("admin@gmail.com")) {
//                                    Toast.makeText(getContext(), "already logged in ", Toast.LENGTH_LONG).show();
//                                    intent = new Intent(getContext(),HomeActivity.class);
//                                    MainActivity.addImagesFragment();
                                    data.put("role", "photographer");
                                }
                                else {
                                    Intent intent = new Intent(getContext(), AdminActivity.class);
                                    data.put("role", "admin");
                                    startActivity(intent);
                                }

                                db.collection("Users").document(user.getUid()).set(data);

                                MainActivity.addImagesFragment();

                            } else {
                                Toast.makeText(getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                    Log.d("checkeddddd", wedCheck + " " + funeralCheck + potraitCheck + birthCheck);

                }
            }
        });



        return v;
    }

}
