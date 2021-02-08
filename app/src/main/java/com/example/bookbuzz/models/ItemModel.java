package com.example.bookbuzz.models;

public class ItemModel {
    private String url;
    private String name;

    public ItemModel() {
    }

    public ItemModel(String  url, String name) {
        this.url = url;
        this.name = name;

    }

    public String getImage() {
        return url;
    }

    public String getName() {
        return name;
    }


}
