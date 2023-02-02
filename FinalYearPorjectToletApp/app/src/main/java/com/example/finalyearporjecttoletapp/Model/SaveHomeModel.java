package com.example.finalyearporjecttoletapp.Model;

public class SaveHomeModel {

    String BathRoom,BedRoom,PropertyType,image,kitchenRoom,location,price,UserId,key;

    public SaveHomeModel() {
    }

    public SaveHomeModel(String bathRoom, String bedRoom, String propertyType, String image, String kitchenRoom, String location, String price, String userId, String key) {
        BathRoom = bathRoom;
        BedRoom = bedRoom;
        PropertyType = propertyType;
        this.image = image;
        this.kitchenRoom = kitchenRoom;
        this.location = location;
        this.price = price;
        UserId = userId;
        this.key = key;
    }

    public String getBathRoom() {
        return BathRoom;
    }

    public void setBathRoom(String bathRoom) {
        BathRoom = bathRoom;
    }

    public String getBedRoom() {
        return BedRoom;
    }

    public void setBedRoom(String bedRoom) {
        BedRoom = bedRoom;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public void setPropertyType(String propertyType) {
        PropertyType = propertyType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKitchenRoom() {
        return kitchenRoom;
    }

    public void setKitchenRoom(String kitchenRoom) {
        this.kitchenRoom = kitchenRoom;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
