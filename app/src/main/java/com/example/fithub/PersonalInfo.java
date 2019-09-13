package com.example.fithub;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class PersonalInfo {


    String id;
    String weight;
    String height;
    String gender;
    String name;
    String birthday;
    String protein;
    String carbs;
    String fat;
    String weightloss;
    String activitylevel;
    String profilepicurl;

    public Map<String, Boolean> stars = new HashMap<>();



    public PersonalInfo()
    {

    }



    public PersonalInfo(String id, String name, String birthday, String weight, String height, String gender, String protein, String carbs, String fat, String weightloss, String activitylevel, String profilepicurl)
    {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.weightloss = weightloss;
        this.activitylevel = activitylevel;
        this.profilepicurl = profilepicurl;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", id);
        result.put("name", name);
        result.put("birth", birthday);
        result.put("weight", weight);
        result.put("height", height);
        result.put("gender", gender);
        result.put("protein", protein);
        result.put("carbs", carbs);
        result.put("fat", fat);
        result.put("weightlosst",weightloss);

        return result;
    }

    public String getProfilepicurl() {
        return profilepicurl;
    }

    public void setProfilepicurl(String profilepicurl) {
        this.profilepicurl = profilepicurl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public void setWeightloss(String weightloss) {
        this.weightloss = weightloss;
    }

    public String getId() {
        return id;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getProtein() {
        return protein;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getFat() {
        return fat;
    }

    public String getWeightloss() {
        return weightloss;
    }

    public String getActivitylevel() {
        return activitylevel;
    }

    public void setActivitylevel(String activitylevel) {
        this.activitylevel = activitylevel;
    }


/*
    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public String getId()
    {
        return id;
    }*/


}
