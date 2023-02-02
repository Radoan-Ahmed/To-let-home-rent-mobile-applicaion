package com.example.finalyearporjecttoletapp.Model;

public class HomeModel {

    String BathRoom,BedRoom,PropertyType,image,kitchenRoom,location,price,UserId,key,division,district,area;

    public HomeModel() {
    }

    /*public HomeModel(String BathRoom, String BedRoom, String PropertyType, String image, String kitchenRoom, String location, String price) {
        this.BathRoom = BathRoom;
        this.BedRoom = BedRoom;
        this.PropertyType = PropertyType;
        this.image = image;
        this.kitchenRoom = kitchenRoom;
        this.location = location;
        this.price = price;
    }*/


    public HomeModel(String BathRoom, String BedRoom, String PropertyType, String image, String kitchenRoom, String location, String price, String UserId, String key, String division, String district, String area) {
        this.BathRoom = BathRoom;
        this.BedRoom = BedRoom;
        this.PropertyType = PropertyType;
        this.image = image;
        this.kitchenRoom = kitchenRoom;
        this.location = location;
        this.price = price;
        this.UserId = UserId;
        this.key = key;
        this.division = division;
        this.district = district;
        this.area = area;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
