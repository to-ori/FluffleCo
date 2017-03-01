package com.example.theflufflecollaboration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 11486248 on 19/02/2017.
 */
public class AdminLogIn extends Activity {
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_log_in);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.Adlogin) {
            EditText a = (EditText) findViewById(R.id.ADUsername);
            String str = a.getText().toString();
            EditText b = (EditText) findViewById(R.id.adminPass);
            String pass = b.getText().toString();

            String password = helper.searchAdminPass(str);
            if (pass.equals(password)) {

                Intent i = new Intent(AdminLogIn.this, Display.class);
                i.putExtra("Username", str);
                startActivity(i);
            } else {

                //dispaly a pop up message to say passswords not the same.
                Toast temp = Toast.makeText(AdminLogIn.this, "Incorrect username or password. \n Please try again.", Toast.LENGTH_SHORT);
                temp.show();
            }
        }
    }
}
