package com.example.millmanagementsystem;

public class MillInformation {
    boolean Morning,Lunch,Dinner;
    public MillInformation(){}
    public MillInformation(boolean morning, boolean lunch, boolean dinner) {
        Morning = morning;
        Lunch = lunch;
        Dinner = dinner;
    }

    public boolean isMorning() {
        return Morning;
    }

    public void setMorning(boolean morning) {
        Morning = morning;
    }

    public boolean isLunch() {
        return Lunch;
    }

    public void setLunch(boolean lunch) {
        Lunch = lunch;
    }

    public boolean isDinner() {
        return Dinner;
    }

    public void setDinner(boolean dinner) {
        Dinner = dinner;
    }
}
