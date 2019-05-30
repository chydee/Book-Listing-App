package com.chydee.booklistingapp;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Books>> {

    //Log Tag
    public static final String LOG_TAG = MainActivity.class.getName();

    // URL for the Books data from the GoogleBooks Dataset

    private static final String url = "https://www.googleapis.com/books/v1/volumes?q=";
    /**
     * Constant value for the book loader ID. any integer can be used
     * This really only comes into play if you're using multiple loaders
     */


    private static final int BOOKS_LOADER_ID = 1;
    //New Url that will be constructed from the search query
    private String newUrl;
    //Adapter for the list of Books
    private BookListAdapter mAdapter;
    //ImageView that comes up when the list is empty
    // or when theres no internet connection
    private ImageView mEmptyStateImageView;
    private TextView mEmptyStateTextView;

    //ProgressView that is displayed when the app is fetching data from
    //the internet
    private ProgressBar mProgressBar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML

        getMenuInflater().inflate(R.menu.main, menu);

            MenuItem searchMenuItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) searchMenuItem.getActionView();
            searchView.setSubmitButtonEnabled(true);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mEmptyStateTextView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    newUrl = url + query;
                    getLoaderManager().restartLoader(BOOKS_LOADER_ID, null, MainActivity.this);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    return false;
                }
            });


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Create a fake list of books
        Find a reference to the {@link ListView} in the layout
         */

        ListView booksListView = findViewById(R.id.list);

        //Create a new adapter that takes an empty list of books as input

        mAdapter = new BookListAdapter(this, new ArrayList<Books>());

        //Set the adapter on the {@link ListView}
        //So the list can be populated in the user interface
        booksListView.setAdapter(mAdapter);

        mEmptyStateImageView = findViewById(R.id.empty_view);
        mEmptyStateTextView = findViewById(R.id.empty_textView);

        mProgressBar = findViewById(R.id.loading_spinner);

        //Check for internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            //Get a reference to the Loadermanager, in order to interact
            //with the loaders

            // Prepare the loader.  Either re-connect with an existing one,
// or start a new one.
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText("Search for Books on Any Topic");
        } else {
            mProgressBar.setVisibility(View.GONE);

            mEmptyStateImageView.setImageResource(R.drawable.error);
        }

        /**
         * Set an item on click listener on the listView Item, which sends
         * an intent to a web browser to which opens the preview page
         * for more information about the book selected
         *
         */

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Find the current book that was clicked on
                Books currentBookclk = mAdapter.getItem(position);

                //Convet the string URL into a URI object (to pass into the
                //intent constructor)
                Uri bookUri = Uri.parse(currentBookclk.getPreviewUrl());

                //Create a new intent to view the book information URI
                Intent websiteintent = new Intent(Intent.ACTION_VIEW, bookUri);

                //Send the intent to launch the new activity
                startActivity(websiteintent);
            }
        });
    }


    @Override
    public Loader<List<Books>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(this, newUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> data) {
        //Clear the adapter of previous book data
        mProgressBar.setVisibility(View.GONE);

        mAdapter.clear();

        //If there is a valid list of Books, then add them to the adapter's
        //Dataset. This will trigger the ListView to update

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);

        }
        mEmptyStateImageView.setVisibility(View.GONE);




    }

    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        mAdapter.clear();
    }


}
