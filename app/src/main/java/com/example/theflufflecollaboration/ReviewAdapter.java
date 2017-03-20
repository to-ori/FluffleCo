package com.example.theflufflecollaboration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11486248 on 19/03/2017.
 */

public class ReviewAdapter extends ArrayAdapter {

    List list = new ArrayList();

    //constructor
    public ReviewAdapter(Context context, int resource) {
        super(context, resource);
    }

    //add method to add reviews tp the list
    public void add(Review review){
        super.add(review);
        list.add(review);
    }

    //gets the number of reviews in the list
    @Override
    public int getCount() {
        return list.size();
    }

    //returns the review at the postion passed into the method
    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    static class ReviewHolder
    {
        TextView tx_title, tx_review, tx_rating, tx_username;


    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        ReviewHolder reviewHolder;
        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.review_row_layout, parent, false);
            reviewHolder= new ReviewHolder();
            reviewHolder.tx_title=(TextView)row.findViewById(R.id.tx_title);
            reviewHolder.tx_review=(TextView)row.findViewById(R.id.tx_content);
            reviewHolder.tx_rating=(TextView)row.findViewById(R.id.tx_rating);
            reviewHolder.tx_username=(TextView)row.findViewById(R.id.tx_user);

            row.setTag(reviewHolder);

        }
        else{
            reviewHolder = (ReviewHolder) row.getTag();
        }

        Review review = (Review) this.getItem(position);
        reviewHolder.tx_title.setText(review.getTitle());
        reviewHolder.tx_review.setText(review.getReviewContent());
        reviewHolder.tx_rating.setText(review.getRating());
        reviewHolder.tx_username.setText(review.getUsername());

        return row;
    }
}
