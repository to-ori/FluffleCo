package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SearchBy extends AppCompatActivity {

    LocalUserDatabase localUserDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localUserDatabase = new LocalUserDatabase(this);
        setContentView(R.layout.activity_search_by);

    }

    public void SearchByProductType(View view){
        startActivity(new Intent(this, SearchByProductType.class));
    }

    public void SearchByProductName(View view){
        startActivity(new Intent(this, SearchByProductName.class));
    }

    public void SearchByPetType(View view){
        startActivity(new Intent(this, SearchByPetType.class));
    }

    public void FluffleCommunity(View view){
        startActivity(new Intent(this, FluffleCommunity.class));
    }

    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }
}
