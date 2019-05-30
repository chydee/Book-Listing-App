package com.chydee.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.List;

public class BooksLoader extends AsyncTaskLoader<List<Books>> {

    //Tag for log messages
    private static final String LOG_TAG = BooksLoader.class.getName();

    //Query URL
    private String mUrl;

    public BooksLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Nullable
    @Override
    public List<Books> loadInBackground() {
        if (mUrl == null){
            return null;
        }

        //Perform the network request, parse the response, and extract a list of books.
        return QueryUtils.fetchBooksData(mUrl);
    }
}
