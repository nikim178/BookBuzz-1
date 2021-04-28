package com.example.bookbuzz.adapter;

public class BooklistHelperClass {


    int image;
    String title;

    public BooklistHelperClass ( int image , String title ) {
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
