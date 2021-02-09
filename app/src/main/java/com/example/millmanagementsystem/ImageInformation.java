package com.example.millmanagementsystem;
public class ImageInformation {
    String name,image,authEmail,type,messid;
    String userId;
    public ImageInformation(){

    }

    public String getMessid() {
        return messid;
    }

    public void setMessid(String messid) {
        this.messid = messid;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuthEmail() {
        return authEmail;
    }

    public void setAuthEmail(String authEmail) {
        this.authEmail = authEmail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ImageInformation(String name, String image, String authEmail, String messid, String type) {
        this.name = name;
        this.image = image;
        this.authEmail=authEmail;
        this.messid=messid;
        this.type=type;
    }

    //Note,Method name must be same with Variable name(variable name must be same with firestore map variable name)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
