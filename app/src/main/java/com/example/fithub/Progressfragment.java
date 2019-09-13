package com.example.fithub;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class Progressfragment extends Fragment {

    private XYPlot plot;
    private FirebaseUser currentuser;
    private FirebaseAuth mAuth;


    DatabaseReference chef;


    public Progressfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progressfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();
        chef = FirebaseDatabase.getInstance().getReference().child("Weight entries").child(currentuser.getUid());


        System.out.println(HomePageActivity.listdates);

       //final ArrayList<String> domainLabels = new ArrayList<>();

        //domainLabels = HomeFragment.listdates;

        // initialize our XYPlot reference:
        plot = (XYPlot) view.findViewById(R.id.plot);

        // create a couple arrays of y-values to plot:
        //final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10, 13, 14};

       // Number[] series1Numbers = {1, 4, 2, 8, 4, 16};

        for(int i = 0; i <HomePageActivity.weights.length; i++)
        {
            System.out.println(HomePageActivity.weights[i]);
        }

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(HomePageActivity.weights), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");
        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:


        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);

        LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);


        // add an "dash" effect to the series2 line:
        series2Format.getLinePaint().setPathEffect(new DashPathEffect(new float[] {

                // always use DP when specifying pixel sizes, to keep things consistent across devices:
                PixelUtils.dpToPix(20),
                PixelUtils.dpToPix(15)}, 0));

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);


        try {
            plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
                @Override
                public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                    int i = Math.round(((Number) obj).floatValue());
                    return toAppendTo.append(HomePageActivity.listdates.get(i));
                }
                @Override
                public Object parseObject(String source, ParsePosition pos) {
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }










    }
