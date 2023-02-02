package com.example.finalyearporjecttoletapp.Model;

public class ChatModel {

    String userId, message;
    long time;

    public ChatModel() {
    }

    public ChatModel(String userId, String message, long time) {
        this.userId = userId;
        this.message = message;
        this.time = time;
    }

    public ChatModel(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
