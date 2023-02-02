package com.example.finalyearporjecttoletapp.Model;

public class MessageModel {

    String name,image, message,userId;

    public MessageModel() {
    }

    public MessageModel(String name, String image, String message,String userId) {
        this.name = name;
        this.image = image;
        this.message = message;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
