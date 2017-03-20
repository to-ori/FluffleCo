package com.example.theflufflecollaboration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {
    EditText name, surname,  username, password, confirmPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.et_name);
        surname = (EditText) findViewById(R.id.et_surname);
        username = (EditText)findViewById(R.id.et_username);
        password = (EditText)findViewById(R.id.et_password);
        confirmPass = (EditText) findViewById(R.id.et_confirm_pass);

    }

    public void onReg(View view){
        String str_name = name.getText().toString();
        String str_surname = surname.getText().toString();
        String str_username = username.getText().toString();
        String str_password = password.getText().toString();
        String str_confirm = confirmPass.getText().toString();
        if(str_password.equals(str_confirm)) {
            String type = "register";
            new Register.BackgroundTask().execute(type, str_name, str_surname, str_username, str_password);
        } else {
            //dispaly a pop up message to say passwords not the same.
            Toast pass = Toast.makeText(Register.this, "Passwords do not match!!", Toast.LENGTH_SHORT);
            pass.show();
        }
    }

    class BackgroundTask extends AsyncTask<String, Void,String> {

        String result;
        String register_url;


        @Override
        protected void onPreExecute() {
            register_url ="http://danu6.it.nuigalway.ie/FluffelCo/register.php";
            result = null;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            URL url = null;

            if (type.equals("register")) {

                try {

                    //create all the variables that you will be passing to the php page, and the database
                    String name = params[1];
                    String email = params[2];
                    String username = params[3];
                    String password = params[4];

                    //create a new url pass it the correct url address for the appropriate php page
                    url = new URL(register_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    //modify the post requset
                    //the & symble seperates the different requests
                    //first you pass the Key, this has been defined in the php file. It needs to be exactly the same as what you called the variable there,
                    //ie $_POST["X"]; X is the variable name you need to type here.
                    //secondly(after the = sign (!!! no spaces!!) ) you pass the value you want to assign to that php variable
                    //first line : name,
                    //second line: email
                    //third line: username
                    //forth line: password

                    String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    //iso-8859-1 is the type od data we are expecting
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    //will return the response
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

    }
}
