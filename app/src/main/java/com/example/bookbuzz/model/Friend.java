package com.example.bookbuzz.model;

public class Friend {
    private String request_type;

    public String getRequest_type() {
        return request_type;
    }
    private Friend(){}
    private Friend(String request_type){
        this.request_type=request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }
}
