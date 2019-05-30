package com.chydee.booklistingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookListAdapter extends ArrayAdapter<Books> {


    private static final String LOG_TAG = BookListAdapter.class.getName();

    /**
     * A custom constructor which does not mirror a superClass
     * The context is used to inflate the layout file, and the list is the data we want
     *
     * @param context   is used to inflate the layout file
     * @param booksList is a list of Books object to be diaplayed in a list of items
     */

    public BookListAdapter(Context context, List<Books> booksList) {
        super(context, 0, booksList);
    }

    /**
     * provides a view for an AdapterView (ListView)
     *
     * @param position    The position of the list of data that should be displaye in the listView
     * @param convertView The recycled view to populate
     * @param parent      The parent ViewGrouo that is used for the inflation
     * @return The View for the position in the Adapterview.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //First check if the existing view is being used otherwise inflate a view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        Bitmap bitmap = null;

        //Get the {@link Books} object loacted at the  positions
        Books currentBook = getItem(position);

        //String requiredUrl;
        if (!currentBook.getThumnail().isEmpty()) {
            ImageView imageViewThumbnail = listItemView.findViewById(R.id.imageview_thumbnail);
            String requiredURL = currentBook.getThumnail();
            //requiredUrl = parts[0] + "s:" + parts[1];
            Picasso.get().load(requiredURL).placeholder(R.drawable.people_eading).into(imageViewThumbnail);
        }


        //Find the views in the list_item.xml Layout file and set the views
        TextView titleTextView = listItemView.findViewById(R.id.textview_title);
        titleTextView.setText(currentBook.getTitle());

        TextView authorsTextView = listItemView.findViewById(R.id.textview_authors);
        authorsTextView.setText(currentBook.getAuthors());

        TextView dateTextView = listItemView.findViewById(R.id.textview_published_on);
        dateTextView.setText(currentBook.getDate());

        RatingBar ratingBar = listItemView.findViewById(R.id.ratings);
        ratingBar.setRating(currentBook.getRatings());

        TextView descriptionTextView = listItemView.findViewById(R.id.textview_description);
        descriptionTextView.setText(currentBook.getDescription());

        return listItemView;


    }
}
