package com.example.theflufflecollaboration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by 11486248 on 19/02/2017.
 */
public class AdminLogIn extends Activity {

EditText et_email, et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_log_in);
        et_email = (EditText) findViewById(R.id.AdminEmail);
        et_password=(EditText) findViewById(R.id.adminPass);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.Adlogin) {

            String email = et_email.getText().toString();
            String password = et_password.getText().toString();

            String type ="adminlogin";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, email, password);

        }
    }
}
