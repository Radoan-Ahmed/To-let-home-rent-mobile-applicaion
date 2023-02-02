package com.example.finalyearporjecttoletapp.Model;

public class UserModel {

    String name,email,phoneNumber,password,image,userId;

    public UserModel() {
    }

    public UserModel(String name, String email,String image) {
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserModel(String name, String email, String phoneNumber, String password,String image,String userId) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.image = image;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
