package com.example.bookbuzz.adapter;

public class WishlistHelperClass {

    int image;
    String title;

    public WishlistHelperClass ( int image , String title ) {
        this.image = image;
        this.title = title;
    }

    public int getImage ( ) {
        return image;
    }

    public String getTitle ( ) {
        return title;
    }
}
