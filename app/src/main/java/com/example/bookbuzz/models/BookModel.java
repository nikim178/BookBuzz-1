package com.example.bookbuzz.models;

public class BookModel {
    private String BookAuth;
    private String BookTitle;
    private String image;


    public BookModel() {
    }

    public BookModel(String BookAuth, String BookTitle, String image) {
        this.BookAuth = BookAuth;
        this.BookTitle = BookTitle;
        this.image = image;

    }

    public String getBookAuth() {
        return BookAuth;
    }

    public void setBookAuth(String bookAuth) {
        BookAuth = bookAuth;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
