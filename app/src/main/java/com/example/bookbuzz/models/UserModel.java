package com.example.bookbuzz.models;

public class UserModel {
    private String userName;
    private String userLocation;
    private String userZipcode;
    private String userEmail;
    private String userProfileURI;
    private String documentId;

    public UserModel() {
    }

    public UserModel(String userName, String userEmail, String userLocation, String userZipcode, String userProfileURI) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userLocation = userLocation;
        this.userZipcode = userZipcode;
        this.userProfileURI = userProfileURI;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getUserProfileURI() {
        return userProfileURI;
    }

    public void setUserProfileURI(String userProfileURI) {
        this.userProfileURI = userProfileURI;
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