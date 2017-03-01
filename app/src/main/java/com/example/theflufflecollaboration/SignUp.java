package com.example.theflufflecollaboration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 11486248 on 10/02/2017.
 */

public class SignUp extends Activity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void onSignUpClick(View v)
    {
        if(v.getId()==R.id.Bsignupbutton){
            EditText name= (EditText)findViewById(R.id.TFname);
            EditText email= (EditText)findViewById(R.id.TFemail);
            EditText newusername= (EditText)findViewById(R.id.TFnewusername);
            EditText pass1= (EditText)findViewById(R.id.TFpass1);
            EditText pass2= (EditText)findViewById(R.id.TFpass2);

            String namestr= name.getText().toString();
            String emailstr= email.getText().toString();
            String newusernamestr= newusername.getText().toString();
            String pass1str= pass1.getText().toString();
            String pass2str= pass2.getText().toString();

            if(!pass1str.equals(pass2str))
            {
                //dispaly a pop up message to say passwords not the same.
                Toast pass = Toast.makeText(SignUp.this, "Passwords do not match!!", Toast.LENGTH_SHORT);
                pass.show();
            } else
            {
                //if they do match insert details into the database.
                Contact c = new Contact();
                c.setName(namestr);
                c.setEmail(emailstr);
                c.setUsername(newusernamestr);
                c.setPass(pass1str);

                helper.insertContact(c);

                //dispaly a pop up message to say passwords not the same.
                Toast pass = Toast.makeText(SignUp.this, "Congratulations you are now registered!! To log in please return to the log in screen", Toast.LENGTH_SHORT);
                pass.show();
            }

        }
    }

}

