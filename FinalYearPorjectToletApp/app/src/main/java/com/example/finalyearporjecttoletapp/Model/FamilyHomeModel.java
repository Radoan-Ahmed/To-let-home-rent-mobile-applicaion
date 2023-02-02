package com.example.finalyearporjecttoletapp.Model;

public class FamilyHomeModel {

    int picture;
    String location, bedRoom, bathRoom, kitchRoom, amount,propertyType;

    public FamilyHomeModel() {
    }

    public FamilyHomeModel(int picture, String location, String bedRoom, String bathRoom, String kitchRoom, String amount) {
        this.picture = picture;
        this.location = location;
        this.bedRoom = bedRoom;
        this.bathRoom = bathRoom;
        this.kitchRoom = kitchRoom;
        this.amount = amount;

    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBedRoom() {
        return bedRoom;
    }

    public void setBedRoom(String bedRoom) {
        this.bedRoom = bedRoom;
    }

    public String getBathRoom() {
        return bathRoom;
    }

    public void setBathRoom(String bathRoom) {
        this.bathRoom = bathRoom;
    }

    public String getKitchRoom() {
        return kitchRoom;
    }

    public void setKitchRoom(String kitchRoom) {
        this.kitchRoom = kitchRoom;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
