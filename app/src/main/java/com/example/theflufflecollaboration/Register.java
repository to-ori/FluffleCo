package com.example.theflufflecollaboration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 11486248 on 10/02/2017.
 */

public class Register extends Activity {

    EditText etname, etemail, etusername, etpassword, etconfirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        etname= (EditText)findViewById(R.id.TVname);
        etemail= (EditText)findViewById(R.id.TVemail);
        etusername= (EditText)findViewById(R.id.TFnewusername);
        etpassword= (EditText)findViewById(R.id.TFpass1);
        etconfirm_password= (EditText)findViewById(R.id.TFpass2);
    }

    public void onLoginClick(View view){
        Intent i = new Intent(Register.this, MainActivity.class);
        startActivity(i);
    }

    public void onRegisterClick(View v)
    {
        String name = etname.getText().toString();
        String email= etemail.getText().toString();
        String username= etusername.getText().toString();
        String password = etpassword.getText().toString();
        String confirm_password = etconfirm_password.getText().toString();

        if(password.equals(confirm_password))
        {

            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, name, email, username, password);

        } else
        {
//            //dispaly a pop up message to say passwords not the same.
            Toast pass = Toast.makeText(Register.this, "Passwords do not match!!", Toast.LENGTH_SHORT);
            pass.show();
        }

    }
}



