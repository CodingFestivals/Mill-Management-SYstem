package com.example.millmanagementsystem;

public class SingleRowDataMillGive {
    String date;
    boolean isSelectedMorning,isSelectedLaunch,isSelectedDinner;

    public SingleRowDataMillGive(){}
    public SingleRowDataMillGive(String date, boolean isSelectedMorning, boolean isSelectedLaunch, boolean isSelectedDinner) {
        this.date = date;
        this.isSelectedMorning = isSelectedMorning;
        this.isSelectedLaunch = isSelectedLaunch;
        this.isSelectedDinner = isSelectedDinner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelectedMorning() {
        return isSelectedMorning;
    }

    public void setSelectedMorning(boolean selectedMorning) {
        isSelectedMorning = selectedMorning;
    }

    public boolean isSelectedLaunch() {
        return isSelectedLaunch;
    }

    public void setSelectedLaunch(boolean selectedLaunch) {
        isSelectedLaunch = selectedLaunch;
    }

    public boolean isSelectedDinner() {
        return isSelectedDinner;
    }

    public void setSelectedDinner(boolean selectedDinner) {
        isSelectedDinner = selectedDinner;
    }
}
