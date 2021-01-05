package com.example.bookbuzz.models;

public class UserModel {
    private String userName;
    private String userLocation;
    private String userZipcode;
    private String userEmail;

    public UserModel() {
    }

    public UserModel(String userName, String userEmail, String userLocation, String userZipcode) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userLocation = userLocation;
        this.userZipcode = userZipcode;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserZipcode() {
        return userZipcode;
    }

    public void setUserZipcode(String userZipcode) {
        this.userZipcode = userZipcode;
    }
}