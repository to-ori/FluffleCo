package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class DisplayProduct extends AppCompatActivity {

    String id, name, description, pet_type, product_type;
    String json_string;
    LocalProductDatabase localProductDatabase;
    LocalUserDatabase localUserDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);
        localProductDatabase = new LocalProductDatabase(this);
        localUserDatabase = new LocalUserDatabase(this);

        //get the stored product from the localdatbase
        Product thisProduct= localProductDatabase.getProductDetails();
        //set string variables equal to associated variables in product;
        id=thisProduct.getId();
        name= thisProduct.getName();
        description=thisProduct.getDescription();
        pet_type=thisProduct.getPet_type();
        product_type = thisProduct.getProduct_type();

        //dispaly these variables by seting the textViews equalt to them
        TextView nametx = (TextView) findViewById(R.id.tx_product_name);
        nametx.setText(name);
        TextView descriptiontx = (TextView) findViewById(R.id.tx_description);
        descriptiontx.setText(description);
        TextView petTypetx = (TextView) findViewById(R.id.tx_pet_type);
        petTypetx.setText(pet_type);
        TextView  productTypetx= (TextView) findViewById(R.id.tx_product_type);
        productTypetx.setText(product_type);

    }

    public void checkString(){
        if(json_string==null){
            Toast.makeText(getApplicationContext(), "No reviews for this product found", Toast.LENGTH_LONG).show();
        }else {

            Intent intent = new Intent(this, DisplayReview.class);
            intent.putExtra("json_data",json_string);
            startActivity(intent );
        }

    }

    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }
    public void onLeaveReview(View view){

        startActivity(new Intent(this, LeaveReview.class));
    }
    public void ViewReviews(View view){
        new BackgroundTask().execute(id);
    }

    class BackgroundTask extends AsyncTask<String, Void,String>
    {
        String result;
        String review_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            review_url="http://danu6.it.nuigalway.ie/FluffelCo/reviews.php";
            result = null;
        }

        @Override
        protected String doInBackground(String... params) {


            String productid = params[0];
            URL url = null;


            try {

                url = new URL(review_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("productId", "UTF-8") + "=" + URLEncoder.encode(productid, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                //iso-8859-1 is the type od data we are expecting
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                result = stringBuilder.toString().trim();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json_string=result;
            checkString();

        }
    }

}
