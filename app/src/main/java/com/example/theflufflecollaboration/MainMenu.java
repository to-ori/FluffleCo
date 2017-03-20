package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    String json_string;

    String name;
    LocalUserDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        localDatabase = new LocalUserDatabase(this);

            Contact contact = localDatabase.getLoggedInUser();
                name = contact.name;


        TextView textView = (TextView) findViewById(R.id.tx_name);
        textView.setText(name);


    }

    public void onUserDetails(View view) {
        Intent i = new Intent(this, DisplayDetails.class);
        startActivity(i);
    }

    public void onLogout(View view){
        localDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }

    public void onSearchProducts(View view){
        startActivity(new Intent(this, SearchBy.class));
    }

}