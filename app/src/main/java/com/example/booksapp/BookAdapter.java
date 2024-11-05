package com.example.booksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.book_title);
        TextView authorTextView = convertView.findViewById(R.id.book_author);
        TextView descriptionTextView = convertView.findViewById(R.id.book_description);
        ImageView thumbnailImageView = convertView.findViewById(R.id.book_thumbnail);

        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        descriptionTextView.setText(book.getDescription());

        if (!book.getThumbnailUrl().isEmpty()) {
            Picasso.get().load(book.getThumbnailUrl()).into(thumbnailImageView);
        }

        return convertView;
    }
}
