package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayReview extends AppCompatActivity {

    LocalUserDatabase localUserDatabase;
    String json_string;

    JSONObject jsonObject;
    JSONArray jsonArray;
    ReviewAdapter reviewAdapter;
    ListView listView;
    LocalProductDatabase localProductDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_review );
        localProductDatabase = new LocalProductDatabase(this);
        localUserDatabase = new LocalUserDatabase(this);
        reviewAdapter = new ReviewAdapter(this, R.layout.review_row_layout);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(reviewAdapter);
        json_string = getIntent().getExtras().getString("json_data");
        Product product= localProductDatabase.getProductDetails();
        String productID =product.getId();
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String id, title, content, rating, user;
            if(jsonArray.length()<1){
                Toast.makeText(getApplicationContext(), "No products found", Toast.LENGTH_LONG).show();
            }
            while(count<jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                id= JO.getString("reviewid");
                title= JO.getString("title");
                content=JO.getString("content");
                rating = JO.getString("rating");
                user = JO.getString("username");

                Review review= new Review(id, title, content, rating, user, productID);
                reviewAdapter.add(review);
                count++;


            }
            String productsFound=count +" reviews found";
            Toast.makeText(getApplicationContext(), productsFound, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }
}
