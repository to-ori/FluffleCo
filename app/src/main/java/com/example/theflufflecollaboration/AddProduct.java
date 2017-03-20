package com.example.theflufflecollaboration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

public class AddProduct extends AppCompatActivity  {
    String pname, description, pet_type, product_types;
    private Spinner spinner;
    private static final String[]paths = {"dog", "cat", "small pets", "other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

    }



}
