package com.example.fithub;

public class CalorieIntake {

    String dBreakfast;
    String dLunch;
    String dDinner;
    String dSupper;
    String dSnacks;

    public CalorieIntake() {
    }

    public CalorieIntake(String dBreakfast, String dLunch, String dDinner, String dSupper, String dSnacks) {
        this.dBreakfast = dBreakfast;
        this.dLunch = dLunch;
        this.dDinner = dDinner;
        this.dSupper = dSupper;
        this.dSnacks = dSnacks;
    }


    public String getdBreakfast() {
        return dBreakfast;
    }

    public void setdBreakfast(String dBreakfast) {
        this.dBreakfast = dBreakfast;
    }

    public String getdLunch() {
        return dLunch;
    }

    public void setdLunch(String dLunch) {
        this.dLunch = dLunch;
    }

    public String getdDinner() {
        return dDinner;
    }

    public void setdDinner(String dDinner) {
        this.dDinner = dDinner;
    }

    public String getdSupper() {
        return dSupper;
    }

    public void setdSupper(String dSupper) {
        this.dSupper = dSupper;
    }

    public String getdSnacks() {
        return dSnacks;
    }

    public void setdSnacks(String dSnacks) {
        this.dSnacks = dSnacks;
    }
}
