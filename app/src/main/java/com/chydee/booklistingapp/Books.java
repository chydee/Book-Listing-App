package com.chydee.booklistingapp;

public class Books {
    //Globals
    private double mRatings;
    private String mThumnail;
    private String mWebUrlLink;
    private String mTextSnippet;
    private String mCategory;
    private String mDate;
    private String mTitle;
    private String mAuthors;
    private String mPublishers;
    private String mDescription;

    /**
     * Create a new array Adapter Object
     * @param ratings for the book ratings
     * @param thumbnail for the book's image.
     * @param webUrlLink for the book's preview page
     * @param category for the category of the book
     * @param date for the date the book was published
     * @param publisher for the publishers' names
     * @param textSnippet to serve as an alternative when theres no description for the book
     * @param title for the title of the book
     * @param authors for the authors' names
     * @param description for the book's description
     */

    public Books(double ratings, String thumbnail, String webUrlLink, String category, String date, String publisher, String textSnippet, String title, String authors, String description){
        this.mRatings = ratings;
        this.mThumnail = thumbnail;
        this.mWebUrlLink = webUrlLink;
        this.mCategory = category;
        this.mDate = date;
        this.mPublishers = publisher;
        this.mTextSnippet = textSnippet;
        this.mTitle = title;
        this.mAuthors = authors;
        this.mDescription = description;
    }

    //Set getter for the variables

    public double getRatings() {
        return mRatings;
    }

    public String getThumnail() {
        return mThumnail;
    }

    public String getWebUrlLink() {
        return mWebUrlLink;
    }

    public String getTextSnippet() {
        return mTextSnippet;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getDate() {
        return mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public String getPublishers() {
        return mPublishers;
    }

    public String getDescription() {
        return mDescription;
    }
}
