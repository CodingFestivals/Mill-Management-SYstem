package com.example.millmanagementsystem;

public class SingleRowDataAmountInformation {
    String senderId,sendingDate,taka,type;

    public SingleRowDataAmountInformation() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getTaka() {
        return taka;
    }

    public void setTaka(String taka) {
        this.taka = taka;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SingleRowDataAmountInformation(String senderId, String sendingDate, String taka, String type) {
        this.senderId = senderId;
        this.sendingDate = sendingDate;
        this.taka = taka;
        this.type = type;
    }
}
