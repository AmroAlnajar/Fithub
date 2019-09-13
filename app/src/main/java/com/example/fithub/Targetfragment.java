package com.example.fithub;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Targetfragment extends Fragment {

    private TextView dateHeader;
    private TextView dailyCalField;
    private EditText breakfastView;
    private EditText lunchView;
    private EditText dinnerView;
    private EditText supperView;
    private EditText snackView;
    private TextView calLeftField;
    private Button btn;



    PersonalInfo post;
    FirebaseUser currentuser;

    CalorieIntake data;

    private FirebaseAuth mAuth;
    DatabaseReference ref;
    DatabaseReference chef;

    double dDailyCal = 0;
    double dBreakfast = 0;
    double dLunch = 0;
    double dDinner = 0;
    double dSupper = 0;
    double dSnacks = 0;


    public Targetfragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_targetfragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    dateHeader = view.findViewById(R.id.dateHeader);
    dailyCalField = view.findViewById(R.id.dailyCalField);
    breakfastView = view.findViewById(R.id.breakfastView);
    lunchView = view.findViewById(R.id.lunchView);
    dinnerView = view.findViewById(R.id.dinnerView);
    supperView = view.findViewById(R.id.supperView);
    snackView = view.findViewById(R.id.snackView);
    calLeftField = view.findViewById(R.id.calLeftField);
    btn = view.findViewById(R.id.btn);

        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();

        final Calculations calculate = new Calculations();


        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("Personal Info").child(currentuser.getUid());


        chef = FirebaseDatabase.getInstance().getReference().child("Calorieintake").child(currentuser.getUid());

        chef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                data = dataSnapshot.getValue(CalorieIntake.class);
                if(data != null)
                {
                    breakfastView.setText(data.getdBreakfast());
                    lunchView.setText(data.getdLunch());
                    dinnerView.setText(data.getdDinner());
                    supperView.setText(data.getdSupper());
                    snackView.setText(data.getdSnacks());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





ref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        post = dataSnapshot.getValue(PersonalInfo.class);


        String age = calculate.getAge(post.getBirthday().trim());

        if(post.getGender().equals("Female"))
        {
            dailyCalField.setText(calculate.BMRcalcFemale(post.getWeight(),post.getHeight(),age));

        }else if (post.getGender().equals("Male"))
        {
            dailyCalField.setText(calculate.BMRcalcMale(post.getWeight(),post.getHeight(),age));

        }

        String cali = calculate.kcal(dailyCalField.getText().toString().trim(), post.getActivitylevel());

        dailyCalField.setText(calculate.weightlosscalories(cali,post.getWeightloss()));


        final Calendar c = Calendar.getInstance();

        int yy = c.get(Calendar.YEAR);
        String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int dd = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        dateHeader.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(dd).append("/").append(month)
                .append("/").append(yy).append(" "));

    }




    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }





});

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String sDailyCal = dailyCalField.getText().toString();
        String sBreakfast = breakfastView.getText().toString();
        String sLunch = lunchView.getText().toString();
        String sDinner = dinnerView.getText().toString();
        String sSupper = supperView.getText().toString();
        String sSnacks = snackView.getText().toString();


        try {
            dDailyCal = Double.parseDouble(sDailyCal);
            dBreakfast = Double.parseDouble(sBreakfast);
            dLunch = Double.parseDouble(sLunch);
            dDinner = Double.parseDouble(sDinner);
            dSupper = Double.parseDouble(sSupper);
            dSnacks = Double.parseDouble(sSnacks);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        double TodaysIntake = dBreakfast + dLunch + dDinner + dSupper + dSnacks;
        double CalLeft = dDailyCal - TodaysIntake;

        String s = Double.toString(CalLeft); //String.valueOf(CalLeft);

        try {
            calLeftField.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }


        CalorieIntake n = new CalorieIntake(sBreakfast, sLunch, sDinner, sSupper, sSnacks);

        ref = FirebaseDatabase.getInstance().getReference().child("Calorieintake").child(currentuser.getUid());

        ref.setValue(n);

    }
});
    }
}
