package com.example.theflufflecollaboration;

/**
 * Created by 11486248 on 19/03/2017.
 */

public class Review {
    private String reviewID, title, reviewContent, rating, username, productid;

    public Review(String id, String title, String review, String rating, String username, String productid){
        setReviewID(id);
        setTitle(title);
        setReviewContent(review);
        setRating(rating);
        setUsername(username);
        setProductid(productid);
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
