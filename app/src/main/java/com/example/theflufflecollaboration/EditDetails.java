package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class EditDetails extends AppCompatActivity {
    LocalUserDatabase localDatabase;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String id, name, email, username, password;
    EditText etname, etemail, etusername, etpassword;
    String tx_name, tx_email, tx_username, tx_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        localDatabase = new LocalUserDatabase(this);

        if(localDatabase.getUserLoggedIn()){
            Contact contact = localDatabase.getLoggedInUser();
            id=contact.id;
            name=contact.name;
            email=contact.email;
            username=contact.username;
            password = contact.password;
        }else {

            Toast.makeText(getApplicationContext(), "To access this page you must login", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, UserLogin.class));
        }
        etname = (EditText)findViewById(R.id.et_name);
        etemail = (EditText)findViewById(R.id.et_email);
        etusername = (EditText)findViewById(R.id.et_username);
        etpassword = (EditText)findViewById(R.id.et_password);

        etname.setText(name);

        etemail.setText(email);
        etusername.setText(username);
        etpassword.setText(password);
    }

    public void onSubmit(View view){

        etname = (EditText)findViewById(R.id.et_name);
        tx_name = etname.getText().toString();
        if(tx_name!=null){
            name=tx_name;
        }
        etemail = (EditText)findViewById(R.id.et_email);
        tx_email= etemail.getText().toString();
        if(tx_email!=null){
            email= tx_email;
        }
        etusername = (EditText)findViewById(R.id.et_username);
        tx_username=etusername.getText().toString();
        if(tx_username!=null){
            username=tx_username;
        }
        etpassword = (EditText)findViewById(R.id.et_password);
        tx_password=etpassword.getText().toString();
        if(tx_password!=null){
            password = tx_password;
        }



        String type="update";
        new EditDetails.BackgroundTask().execute(type, id, name, email, username, password);

    }
    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    public void onLogout(View view){
        localDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }

    public void checkUser(){
        if(json_string==null){
            Toast.makeText(getApplicationContext(), "an error has occurred please try again.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "Details updated.", Toast.LENGTH_LONG).show();
            Contact contact = new Contact(id, name, email, username, password);
            localDatabase.storeData(contact);
            startActivity(new Intent(this, DisplayDetails.class));
        }
    }

    class BackgroundTask extends AsyncTask<String, Void,String> {
        String JSON_STRING;
        String result;
        String update_url;


        @Override
        protected void onPreExecute() {
            update_url ="http://danu6.it.nuigalway.ie/FluffelCo/updateUserDetails.php";
            result = null;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            URL url = null;

            if (type.equals("update")) {

                try {

                    //create all the variables that you will be passing to the php page, and the database
                    String id=params[1];
                    String name = params[2];
                    String email = params[3];
                    String username = params[4];
                    String password = params[5];

                    //create a new url pass it the correct url address for the appropriate php page
                    url = new URL(update_url);
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

                    String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                            + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
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
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((JSON_STRING = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON_STRING + "\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    result = stringBuilder.toString().trim();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json_string = result;
            checkUser();
        }

    }

}
