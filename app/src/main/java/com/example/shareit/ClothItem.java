package com.example.shareit;

public class ClothItem {

    String DonorID, DonorName, DonorNumber, ClothName, ClothCount;
    Double  Latitude, Longitude;
    Boolean Status;

    public ClothItem(String donorID, String donorName, String donorNumber, String clothName, String clothCount, Double latitude, Double longitude, Boolean status) {
        DonorID = donorID;
        DonorName = donorName;
        DonorNumber = donorNumber;
        ClothName = clothName;
        ClothCount = clothCount;
        Latitude = latitude;
        Longitude = longitude;
        Status = status;
    }

    public ClothItem() {
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public String getDonorID() {
        return DonorID;
    }

    public void setDonorID(String donorID) {
        DonorID = donorID;
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

    public String getClothName() {
        return ClothName;
    }

    public void setClothName(String clothName) {
        ClothName = clothName;
    }

    public String getClothCount() {
        return ClothCount;
    }

    public void setClothCount(String clothCount) {
        ClothCount = clothCount;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }
}
