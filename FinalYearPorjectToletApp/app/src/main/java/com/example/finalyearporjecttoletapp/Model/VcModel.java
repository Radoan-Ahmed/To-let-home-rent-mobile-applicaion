package com.example.finalyearporjecttoletapp.Model;

public class VcModel {
    String key, responce;

    public VcModel() {
    }

    public VcModel(String key, String responce) {
        this.key = key;
        this.responce = responce;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }
}
