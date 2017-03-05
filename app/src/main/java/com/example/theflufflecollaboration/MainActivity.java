    package com.example.theflufflecollaboration;

    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.EditText;

    public class MainActivity extends AppCompatActivity {

        EditText etusername, etpassword;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            etusername = (EditText) findViewById(R.id.TVusername);
            etpassword = (EditText) findViewById(R.id.TVpassword);
        }

        public void onRegisterClick(View v) {
            Intent i = new Intent(MainActivity.this, Register.class);

            startActivity(i);
        }

        public void onLoginClick(View v) {

            String username = etusername.getText().toString();
            String password = etpassword.getText().toString();

            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, username, password);

        }

    }
