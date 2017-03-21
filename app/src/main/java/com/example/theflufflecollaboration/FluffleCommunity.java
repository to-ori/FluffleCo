package com.example.theflufflecollaboration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FluffleCommunity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluffle_community);
    }
    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }
}
