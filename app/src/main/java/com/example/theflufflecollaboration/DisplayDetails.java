package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class DisplayDetails extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String id, name, email, username, password;
    LocalUserDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        localDatabase = new LocalUserDatabase(this);

        if(localDatabase.getUserLoggedIn()){
            Contact contact = localDatabase.getLoggedInUser();
            name=contact.name;
            email=contact.email;
            username=contact.username;
            password = contact.password;
        }

        TextView textViewName = (TextView) findViewById(R.id.tx_name);
        textViewName.setText(name);

        TextView textViewEmail = (TextView) findViewById(R.id.tx_email);
        textViewEmail.setText(email);

        TextView textViewUname = (TextView) findViewById(R.id.tx_username);
        textViewUname.setText(username);

        TextView textViewpassword = (TextView) findViewById(R.id.tx_password);
        textViewpassword.setText(password);

    }

    public void onLogout(View view){
        localDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }

    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    public void onEdit(View view) {
        Intent i = new Intent(this, EditDetails.class);

        startActivity(i);
    }

}
