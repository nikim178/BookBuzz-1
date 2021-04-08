package com.example.bookbuzz.model;

public class Friend {
    private String request_type;
    private String name;
    private String profile;
    private String documentId;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getRequest_type() {

        return request_type;
    }
    private Friend(){}
    private Friend(String request_type,String name){

        this.request_type=request_type;
        this.name=name;
    }
    public void setRequest_type(String request_type,String name) {
        this.request_type = request_type;
        this.name=name;
    }
}
