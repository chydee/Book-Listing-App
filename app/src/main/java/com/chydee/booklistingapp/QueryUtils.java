package com.chydee.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a
     * {@link QueryUtils} object.
     * This class is only meant to hold static variable and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed)
     */

    private QueryUtils() {
        // required Empty private constructor
    }

    /**
     * Query the Google Book dataset and return on {@link Books} object to
     * represent a single earthquake
     */

    //Returns new URL object from the given string URL
    private static URL createUrl(String queryURL) {

        URL url = null;
        try {
            url = new URL(queryURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    //make an HTTP request to the given URL and returbn a String as the response
    private static String makeHttpRequest(URL url) throws IOException {
        Log.i(LOG_TAG, "Connecting to the internet...");
        String jsonResponse = "";

        //If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000/*milliseconds*/);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If the request is successful
            //Then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error, response code: " + urlConnection.getResponseCode());
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect(); //cancel connection
            }
            if (inputStream != null) {
                inputStream.close();//Close input stream
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@Link InputStream} into a String which contains
     * the whole JSON response from the server
     */

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return on the {@link Books} object by parsing out the information
     * about the books detail from the input booksJson string
     */

    private static List<Books> extractFeatueFromJSON(String booksJSON) {
        //If the JSON String is empty or null, then return early
        if (TextUtils.isEmpty(booksJSON)) {
            return null;
        }

        //Create an empty ArrayList that we can start adding earthquake to
        List<Books> books = new ArrayList<>();
        //Try to poarse the JSON response String. If there is a problem with'
        //the way the JSON is formatted
        // a JSONException exception object will be thrown
        //Catch the exception so the app doesn't crash, and print the error message to the Logcat

        try {
            //Create a JSONObject from the JSON response String
            JSONObject baseJsonResponse = new JSONObject(booksJSON);

            /*
            Extract the JSONArray associated with the key called "items"
            which represents a list of items (or books' details)
             */
            JSONArray booksArray = baseJsonResponse.optJSONArray("items");
            // For each Book in the booksArray, create {@link Books} object
            for (int i = 0; i < booksArray.length(); i++) {
                //Get a single book, extract the JSONObject associated with
                //the keys called "volumeInfo" and the "searchInfo"
                //which represents a list of all the properties of a particular book
                JSONObject currentBook = booksArray.optJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                JSONObject searchInfo = currentBook.getJSONObject("searchInfo");
                /**
                 * Extract the value for the following keys:
                 * title, authors, publisher, publishedDate, description,
                 * categories, ratingCount, thumbnail and the previewUrl
                 */

                String title = volumeInfo.optString("title");
                JSONArray bookAuthors = volumeInfo.optJSONArray("authors");
                //Get the name of the first author from the authors array
                String authors = "";
                if (bookAuthors.length() > 1) {
                    for (int j = 0; j < bookAuthors.length(); j++) {
                        authors = bookAuthors.getString(j) + " and " + authors;
                    }
                } else {
                    authors = bookAuthors.optString(0);
                }

                String publisher = volumeInfo.optString("publisher");
                String date = volumeInfo.optString("publishedDate");
                String description = volumeInfo.optString("description");
                //check if the description is available
                if (description == null) {
                    //Use the textSnippet in place of the description
                    description = searchInfo.optString("textSnippet");
                }
                int ratingsCount = volumeInfo.optInt("ratingsCount");
                String imageURL = "";
                if (volumeInfo.has("imageLinks")) {
                    JSONObject imageLinksObject = volumeInfo.optJSONObject("imageLinks");
                    if (imageLinksObject.has("thumbnail")) {
                        imageURL = imageLinksObject.optString("thumbnail");

                    }

                }


               /* JSONArray categories = volumeInfo.optJSONArray("categories");
                String mainCategory = "";
                for (int x = 0; x < categories.length(); x++){
                     mainCategory = categories.getString(x);
                }*/
                String previewURL = volumeInfo.optString("infoLink");

                /**
                 * Create a new {@link Books} object with the keys
                 * from the JSON response
                 */

                Books books1 = new Books(ratingsCount, imageURL, previewURL, date, publisher, title, authors, description);

                books.add(books1);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the books' JSON results", e);
        }
        return books;
    }

    /**
     * Query the GoogleBooks' dataset and return a list of {@link Books}
     * objects
     */

    public static List<Books> fetchBooksData(String requestUrl) {
       /* try {
            Thread.sleep(2000);// To delay the displaying of the data just to show the progress bar
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //Create URL object
        URL url = createUrl(requestUrl);

        //Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making HTTP request...", e);
        }
        //Extract relevant fields from the JSON response and create a list of
        //{@link Books}

        List<Books> newBooks = extractFeatueFromJSON(jsonResponse);
        return newBooks;
    }


}
