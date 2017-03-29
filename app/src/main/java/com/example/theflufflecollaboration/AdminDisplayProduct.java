package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class AdminDisplayProduct extends AppCompatActivity {

    String id, name, description, pet_type, product_type;
    String json_string;
    LocalProductDatabase localProductDatabase;
    LocalUserDatabase localUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_display_product);

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

    public void onMainMenu(View view){
        startActivity(new Intent(this, OpenPage.class));
    }

}
