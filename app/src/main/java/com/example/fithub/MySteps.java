package com.example.fithub;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MySteps extends Fragment implements SensorEventListener {

    private TextView tv_steps;
    private int stepsInSensor = 0;
    private int stepsAtReset;

    SensorManager sensorManager;


    boolean running = false;


    public MySteps() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_steps, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_steps = view.findViewById(R.id.tv_steps);

        try {
            sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener( this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(getActivity(), "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        running = false;
        //if you unregister the hardware will stop detecting steps
        //sensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running){
            stepsInSensor = Integer.valueOf((int) event.values[0]);
            int stepsSinceReset = stepsInSensor - stepsAtReset - 10;
            tv_steps.setText(String.valueOf(stepsSinceReset));

           // tv_steps.setText(String.valueOf(stepsSinceReset));

//            tv_steps.setText(String.valueOf(event.values[0]));

        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
