package com.example.fithub;

public class WeightEntries {

    String date;
    String weight;
    String pic;


    public WeightEntries() {
    }


    public WeightEntries(String date, String weight, String pic) {
        this.date = date;
        this.weight = weight;
        this.pic = pic;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
