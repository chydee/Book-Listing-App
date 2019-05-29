package com.chydee.booklistingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    //Log Tag
    public static final String LOG_TAG = MainActivity.class.getName();

    // URL for the Books data from the GoogleBooks Dataset

    private static final String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes";
    /**
     * Constant value for the book loader ID. any integer can be used
     * This really only comes into play if you're using multiple loaders
     *
     */

    private static final int BOOKS_LOADER_ID = 1;
    //Adapter for the list of Books
    private BookListAdapter mAdapter;
    //ImageView that comes up when the list is empty
    // or when theres no internet connection
    private ImageView mEmptyStateImageView;

    //ProgressView that is displayed when the app is fetching data from
    //the internet
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
