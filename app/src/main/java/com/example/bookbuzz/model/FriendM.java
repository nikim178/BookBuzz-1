package com.example.bookbuzz.model;

public class FriendM {
    private String idChatRoom;
    private String name;
    private String profile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public FriendM () {}

    public FriendM(String idChatRoom) {
        this.idChatRoom = idChatRoom;
    }

    public String getIdChatRoom() {
        return idChatRoom;
    }

    public void setIdChatRoom(String idChatRoom) {
        this.idChatRoom = idChatRoom;
    }
}



