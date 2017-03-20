package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LeaveReview extends AppCompatActivity {


    Product product;
    LocalProductDatabase localProductDatabase;
    LocalUserDatabase localUserDatabase;
    String reviewTitle, reviewContent, reviewRating, userID, productID, productName;
    int reviewRatingint;
    EditText et_title, et_rating, et_review;
    TextView tx_ProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_review);
        localProductDatabase =new LocalProductDatabase(this);
        localUserDatabase = new LocalUserDatabase(this);

        //get textview from xml page
        tx_ProductName= (TextView) findViewById(R.id.tx_reviewProductName);
        et_title = (EditText) findViewById(R.id.et_ratingTitle);
        et_rating= (EditText) findViewById(R.id.et_reviewRating);
        et_review =(EditText) findViewById(R.id.et_reviewContect);

        Contact contact = localUserDatabase.getLoggedInUser();
        userID=contact.id;
        product= localProductDatabase.getProductDetails();
        productID=product.getId();
        productName=product.getName();
        tx_ProductName.setText(productName);



    }

    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }

    public void onReviewSubmit(View view){
        //take the input form the user and set variables equal to it.
        reviewTitle=et_title.getText().toString();
        reviewContent = et_review.getText().toString();
        reviewRating = et_rating.getText().toString();
        reviewRatingint=Integer.parseInt(reviewRating);
        //here we check to make sure the rating is between 1 and 5, and make corrections if needed
        if(reviewRatingint>5 ) {
            Toast.makeText(getApplicationContext(), "Ratings are from 1-5, anything higher will be taken as 5", Toast.LENGTH_LONG).show();
            reviewRatingint=5;
        }
        if(reviewRatingint<1){
            Toast.makeText(getApplicationContext(), "Ratings are from 1-5, anything lower will be taken as 1", Toast.LENGTH_LONG).show();
            reviewRatingint=1;
        }

        new BackgroundTask().execute();
    }

    //this class interacts with the PHP file that interacts with the server
    class BackgroundTask extends AsyncTask<Void, Void,String> {

        String result;
        String submitReview_url;


        @Override
        protected void onPreExecute() {
            submitReview_url ="http://danu6.it.nuigalway.ie/FluffelCo/submitReview.php";
            result = null;

        }

        @Override
        protected String doInBackground(Void... params) {

            URL url = null;

             try {

                    reviewRating = String.valueOf(reviewRatingint);

                    //create a new url pass it the correct url address for the appropriate php page
                    url = new URL(submitReview_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    //modify the post requset
                    //the & symble seperates the different requests
                    //first you pass the Key, this has been defined in the php file. It needs to be exactly the same as what you called the variable there,
                    //ie $_POST["X"]; X is the variable name you need to type here.
                    //secondly(after the = sign (!!! no spaces!!) ) you pass the value you want to assign to that php variable


                    String post_data = URLEncoder.encode("reviewTitle", "UTF-8") + "=" + URLEncoder.encode(reviewTitle, "UTF-8") + "&"
                            + URLEncoder.encode("reviewContent", "UTF-8") + "=" + URLEncoder.encode(reviewContent, "UTF-8") + "&"
                            + URLEncoder.encode("reviewRating", "UTF-8") + "=" + URLEncoder.encode(reviewRating, "UTF-8") + "&"
                            + URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8")+ "&"
                            + URLEncoder.encode("productID", "UTF-8") + "=" + URLEncoder.encode(productID, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    //iso-8859-1 is the type od data we are expecting
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    //will return the response
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }
}
