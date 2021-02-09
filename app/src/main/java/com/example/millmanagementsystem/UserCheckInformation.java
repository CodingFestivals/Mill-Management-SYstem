package com.example.millmanagementsystem;

public class UserCheckInformation {
    String messid,adminEmail,adminPass,type,authEmail,authPass;

    public String getAuthEmail() {
        return authEmail;
    }

    public void setAuthEmail(String authEmail) {
        this.authEmail = authEmail;
    }

    public String getAuthPass() {
        return authPass;
    }

    public void setAuthPass(String authPass) {
        this.authPass = authPass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserCheckInformation(){}

    public UserCheckInformation(String messid, String adminEmail, String adminPass,String type,String authEmail,String authPass) {
        this.messid = messid;
        this.adminEmail = adminEmail;
        this.adminPass = adminPass;
        this.type=type;
        this.authEmail=authEmail;
        this.authPass=authPass;
    }

    public String getMessid() {
        return messid;
    }

    public void setMessid(String messid) {
        this.messid = messid;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }
}
