package com.chydee.booklistingapp;

public class Books {
    //Globals
    private int mRatings;
    private String mThumnail;
    private String mPreviewUrl;
    //private String mCategory;
    private String mDate;
    private String mTitle;
    private String mAuthors;
    private String mPublishers;
    private String mDescription;

    /**
     * Create a new array Adapter Object
     * @param ratings for the book ratings
     * @param thumbnail for the book's image.
     * @param previewUrl for the book's preview page
     * @param date for the date the book was published
     * @param publisher for the publishers' names
     * @param title for the title of the book
     * @param authors for the authors' names
     * @param description for the book's description
     */

    public Books(int ratings, String thumbnail, String previewUrl, String date, String publisher, String title, String authors, String description){
        this.mRatings = ratings;
        this.mThumnail = thumbnail;
        this.mPreviewUrl = previewUrl;
       // this.mCategory = category;
        this.mDate = date;
        this.mPublishers = publisher;
        this.mTitle = title;
        this.mAuthors = authors;
        this.mDescription = description;
    }

    //Set getter for the variables

    public float getRatings() {
        return mRatings;
    }

    public String getThumnail() {
        return mThumnail;
    }

    public String getPreviewUrl() {
        return mPreviewUrl;
    }

   // public String getCategory() {
       // return mCategory;
   // }

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
