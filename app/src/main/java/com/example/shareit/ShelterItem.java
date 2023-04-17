package com.example.shareit;

public class ShelterItem {

    String DonorID, DonorName, DonorNumber, ShelterDesc, Availability;
    Double  Latitude, Longitude;
    Boolean Status;

    public ShelterItem(String donorID, String donorName, String donorNumber, String shelterDesc, String availability, Double latitude, Double longitude, Boolean status) {
        DonorID = donorID;
        DonorName = donorName;
        DonorNumber = donorNumber;
        ShelterDesc = shelterDesc;
        Availability = availability;
        Latitude = latitude;
        Longitude = longitude;
        Status = status;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public ShelterItem() {
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

    public String getShelterDesc() {
        return ShelterDesc;
    }

    public void setShelterDesc(String shelterDesc) {
        ShelterDesc = shelterDesc;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
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
