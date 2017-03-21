package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity {
    LocalProductDatabase localProductDatabase;
    LocalUserDatabase localUserDatabase;
    String json_string;

    JSONObject jsonObject;
    JSONArray jsonArray;
    ProductAdapter productAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_listview_layout);
        localProductDatabase = new LocalProductDatabase(this);
        localUserDatabase= new LocalUserDatabase(this);
        productAdapter = new ProductAdapter(this, R.layout.row_layout);
        listView = (ListView) findViewById(R.id.listview);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = (Product) listView.getItemAtPosition(position);
                localProductDatabase.storeProduct(selectedProduct);
                Intent i = new Intent (DisplayListView.this, DisplayProduct.class );
                startActivity(i);

            }
        });
        listView.setAdapter(productAdapter);
        json_string = getIntent().getExtras().getString("json_data");

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String id, name, description, pet_type, product_type;

            while(count<jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                id= JO.getString("id");
                name= JO.getString("name");
                description=JO.getString("description");
                pet_type = JO.getString("pet_type");
                product_type = JO.getString("product_type");

                Product product= new Product(id, name, description, pet_type, product_type);
                productAdapter.add(product);
                count++;
            }
            String productsFound=count +" products found";
            Toast.makeText(getApplicationContext(), productsFound, Toast.LENGTH_LONG).show();


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
