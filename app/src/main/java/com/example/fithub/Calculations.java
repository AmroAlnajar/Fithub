package com.example.fithub;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Calculations {

    public String BMIcalc(String weight, String height)
    {
        Double weights = Double.parseDouble(weight);
        Double heights = Double.parseDouble(height);

        double BMI = weights/(heights*heights)*10000;
        double finalbmi = Math.round(BMI * 10)/10.0;

        return Double.toString(finalbmi);

    }

    public String idealweightcalc(String height)
    {
        Double heights = (Double.parseDouble(height)-152)/2.54;

        Double idealweight = 50 + (heights*2.54);

        int s = (int) Math.round(idealweight);

        return Integer.toString(s);
    }

    public String idealweightcalcfemale(String height)
    {
        Double heights = (Double.parseDouble(height)-152)/2.54;

        Double idealweight = 45 + (heights*2.54);

        int s = (int) Math.round(idealweight);

        return Integer.toString(s);
    }


    public String BMRcalcMale (String weight, String height, String age)
    {
        double weighttodouble = Double.parseDouble(weight);
        double heighttodouble = Double.parseDouble(height);
        double agetodouble = Double.parseDouble(age);

        double BMRmale = (heighttodouble * 6.25) + (weighttodouble * 9.99) - (agetodouble * 4.92) + 5 ;

        double bmrfinal = Math.round(BMRmale);

        return Double.toString(bmrfinal);
    }


    public String BMRcalcFemale (String weight, String height, String age)
    {
        double weighttodouble = Double.parseDouble(weight);
        double heighttodouble = Double.parseDouble(height);
        double agetodouble = Double.parseDouble(age);

        double BMRfemale = (heighttodouble * 6.25) + (weighttodouble * 9.99) - (agetodouble * 4.92) - 161 ;

        double bmrfinal = Math.round(BMRfemale);

        return Double.toString(bmrfinal);
    }


    public String kcal(String BMR, String activitylevel)
    {
        Double bmr = Double.parseDouble(BMR);
        Double al = Double.parseDouble(activitylevel);

        //DecimalFormat df = new DecimalFormat("#######.#");

        //Double kcalintake = Double.parseDouble   (df.format(bmr*al));

        return Double.toString(bmr*al);
    }


    public String weightlosscalories(String kcal, String weightloss)
    {
        Double caloriekcal = Double.parseDouble(kcal);
        Double weightlosst = Double.parseDouble(weightloss);

        Double weightlossccalories = caloriekcal-weightlosst;


        return weightlossccalories.toString().trim();
    }





    public String getAge(String birthday) {

        String bday [] = birthday.split("/");

        int year = Integer.parseInt(bday[2]);
        int month = Integer.parseInt(bday[1]);
        int day = Integer.parseInt(bday[0]);

        Calendar dob = Calendar.getInstance();

        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1,
                today.get(Calendar.DATE));

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {

            age--;

        }

        Integer ageInt = new Integer(age);

        // String ageS = ageInt.toString();


        return ageInt.toString();
    }



}
