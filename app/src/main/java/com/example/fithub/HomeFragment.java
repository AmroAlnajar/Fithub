package com.example.fithub;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment  {

    private TextView bmifield;
    private TextView bmrfield;
    private TextView kcal;
    private TextView protein;
    private TextView carbs;
    private TextView fat;
    private TextView currentweight;
    private TextView idealweight;
    PersonalInfo post;
    FirebaseUser currentuser;




    private FirebaseAuth mAuth;
    DatabaseReference ref;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view =  inflater.inflate(R.layout.fragment_homefragment, container, false);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homefragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        bmifield = view.findViewById(R.id.bmifield);
        bmrfield = view.findViewById(R.id.bmrfield);
        kcal = view.findViewById(R.id.kcalfield);
        protein = view.findViewById(R.id.proteinfield);
        carbs = view.findViewById(R.id.carbfield);
        fat = view.findViewById(R.id.fatfield);
        currentweight = view.findViewById(R.id.currentweight);
        idealweight = view.findViewById(R.id.idealweight);



        super.onViewCreated(view, savedInstanceState);



}



    @Override
    public void onStart() {
        super.onStart();
        final Calculations calculate = new Calculations();

        mAuth = FirebaseAuth.getInstance();

        currentuser = mAuth.getCurrentUser();
        if(currentuser == null)
        {
            System.out.println("no user is logged in!!!");
        }
        else
        {
            System.out.println("user is logged in!");
        }


        // Get a reference to our posts
        //   final FirebaseDatabase database = FirebaseDatabase.getInstance();


        // DatabaseReference ref = database.getReference("Personal Info");

        //   FirebaseDatabase database = FirebaseDatabase.getInstance().getReference().child("Personal Info").child(currentuser.getUid());

        ref = FirebaseDatabase.getInstance().getReference().child("Personal Info").child(currentuser.getUid());


// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post = dataSnapshot.getValue(PersonalInfo.class);

                if(post!= null)
                {


                    String age = calculate.getAge(post.getBirthday().trim());

                    System.out.println("the age from age calculator is: " + age);


                    bmifield.setText(calculate.BMIcalc(post.getWeight(),post.getHeight()));

                    if(post.getGender().equals("Male"))
                    {
                        bmrfield.setText(calculate.BMRcalcMale(post.getWeight(),post.getHeight(),age));
                        idealweight.setText(calculate.idealweightcalc(post.getHeight()));

                    }
                    if(post.getGender().equals("Female"))
                    {
                        idealweight.setText(calculate.idealweightcalcfemale(post.getHeight()));
                        bmrfield.setText(calculate.BMRcalcFemale(post.getWeight(),post.getHeight(),age));
                    }



             /*     UserName.setText(post.getName());
                    UserEmail.setText(currentuser.getEmail()); */

                    String calorieintakewithoutloss = calculate.kcal(bmrfield.getText().toString().trim(), post.getActivitylevel());

                    for(int i = 0; i < 10; i++)
                    {
                        System.out.println(calorieintakewithoutloss);
                    }



                    kcal.setText(calculate.weightlosscalories(calorieintakewithoutloss, post.getWeightloss()));
                    currentweight.setText(post.getWeight());
                    protein.setText(post.getProtein()+"%");
                    carbs.setText(post.getCarbs()+"%");
                    fat.setText(post.getFat()+"%");



                    System.out.println(post.getId());
                    System.out.println(post.getName());
                    System.out.println(post.getGender().trim());



                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




    }


}
