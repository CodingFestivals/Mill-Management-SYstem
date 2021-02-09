package com.example.millmanagementsystem;

public class MessageInformation {
    String message,senderId,type,sendingDate;

    public MessageInformation() {
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public MessageInformation(String message, String senderId, String type, String sendingDate) {
        this.message = message;
        this.senderId = senderId;
        this.type = type;
        this.sendingDate = sendingDate;
    }
}
