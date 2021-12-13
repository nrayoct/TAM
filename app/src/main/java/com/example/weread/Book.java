package com.example.weread;

public class Book {
    //model class
    private String title, imageURL, pdfURL;
    private String author;
    private String id;

    //constructor
    public Book() {
        //needed for firebase
    }
    public Book(String title, String imageURL, String pdfURL, String author, String id) {
        this.title = title;
        this.imageURL = imageURL;
        this.pdfURL = pdfURL;
        this.author = author;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
