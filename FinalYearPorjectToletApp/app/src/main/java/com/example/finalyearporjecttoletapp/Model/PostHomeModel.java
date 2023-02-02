package com.example.finalyearporjecttoletapp.Model;

public class PostHomeModel {

    String BathRoom,BedRoom,PropertyType,image,kitchenRoom,location,price,UserId,key,Division,District,Area;

    public PostHomeModel() {
    }

    public PostHomeModel(String bathRoom, String bedRoom, String propertyType, String image, String kitchenRoom, String location, String price, String userId, String key,String Division,String District,String Area) {
        BathRoom = bathRoom;
        BedRoom = bedRoom;
        PropertyType = propertyType;
        this.image = image;
        this.kitchenRoom = kitchenRoom;
        this.location = location;
        this.price = price;
        UserId = userId;
        this.key = key;
        this.Division = Division;
        this.District = District;
        this.Area = Area;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
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

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }
}
