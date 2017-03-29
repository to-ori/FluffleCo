package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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

public class UserLogin extends AppCompatActivity {

    JSONObject jsonObject;
    JSONArray jsonArray;
    String id, name, email, username, password;
    LocalUserDatabase localDatabase;
    String json_string;

    EditText UsernameEt, PasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        localDatabase = new LocalUserDatabase(this);
        UsernameEt = (EditText)findViewById(R.id.etUserName);
        PasswordEt = (EditText)findViewById(R.id.etPassword);
    }

    public  void openReg(View view){
        startActivity(new Intent(this, Register.class));
    }



    class BackgroundTask extends AsyncTask<String, Void,String> {

        String result;
        String login_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            login_url = "http://danu6.it.nuigalway.ie/FluffelCo/loginjson.php";
            result = null;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            URL url = null;

            if (type.equals("login")) {
                try {

                    String user_name = params[1];
                    String password = params[2];

                    url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
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

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json_string = result;
            checkUser();
        }

    }

    public void onLogin(View view){
        String username = UsernameEt.getText().toString();
        String passowrd = PasswordEt.getText().toString();
        String type = "login";
        new BackgroundTask().execute(type, username, passowrd);
    }


    public void checkUser(){

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            if(jsonArray.length()==0){
                Toast.makeText(getApplicationContext(), "Login details incorrect, please try again or register as a new user", Toast.LENGTH_LONG).show();
            } else{
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                id = JO.getString("id");
                name = JO.getString("name");
                email = JO.getString("email");
                username = JO.getString("username");
                password = JO.getString("password");
                Contact contact = new Contact(id, name, email, username, password);
                localDatabase.storeData(contact);
                localDatabase.setUserLoggedIn(true);
                    Intent intent = new Intent(this, MainMenu.class);
                    intent.putExtra("json_data",json_string);
                    startActivity(intent );
                count++;}
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
