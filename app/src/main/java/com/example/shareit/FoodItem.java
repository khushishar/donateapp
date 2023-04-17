package com.example.shareit;

public class FoodItem {
    String DonorID, DonorName, DonorNumber, FoodName, FoodCount;
    Double  Latitude, Longitude;
    Boolean Status;

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public FoodItem(String donorID, String donorName, String donorNumber, String foodName, String foodCount, Double latitude, Double longitude, Boolean status) {
        DonorID = donorID;
        DonorName = donorName;
        DonorNumber = donorNumber;
        FoodName = foodName;
        FoodCount = foodCount;
        Latitude = latitude;
        Longitude = longitude;
        Status = status;
    }

    public FoodItem() {
    }


    public String getDonorID() {
        return DonorID;
    }

    public void setDonorID(String donorID) {
        DonorID = donorID;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public String getDonorName() {
        return DonorName;
    }

    public void setDonorName(String donorName) {
        DonorName = donorName;
    }

    public String getDonorNumber() {
        return DonorNumber;
    }

    public void setDonorNumber(String donorNumber) {
        DonorNumber = donorNumber;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodCount() {
        return FoodCount;
    }

    public void setFoodCount(String foodCount) {
        FoodCount = foodCount;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLocLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }
}
