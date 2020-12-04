package com.example.bookbuzz;

public class UserProfile {
    public String uName;
    public String uEmail;

    public UserProfile(){

    }

    public UserProfile(String uName,String uEmail){
        this.uName = uName;
        this.uEmail = uEmail;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }
}
