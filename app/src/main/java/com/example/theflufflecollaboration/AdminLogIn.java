package com.example.theflufflecollaboration;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

/**
 * Created by 11486248 on 19/02/2017.
 */
public class AdminLogIn extends Activity {
    String email, password;
    EditText et_email, et_password;
    String loggedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_log_in);
        et_email = (EditText) findViewById(R.id.AdminEmail);
        et_password = (EditText) findViewById(R.id.adminPass);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.Adlogin) {

            email = et_email.getText().toString();
            password = et_password.getText().toString();

            new BackgroundTask().execute();

        }
    }

    public void verify(){
        if(loggedin.equals("true")){
            startActivity(new Intent(this, AddProduct.class));
        }else{
            Toast.makeText(getApplicationContext(), "Login details incorrect, please try again. If you are not a FluffleCo admin please log in as a normal user.", Toast.LENGTH_LONG).show();

        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String result;
        String admin_login_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            admin_login_url = "http://danu6.it.nuigalway.ie/FluffelCo/adminlogin.php";
            result = null;
        }

        @Override
        protected String doInBackground(Void... params) {

            URL url = null;


            try {

                url = new URL(admin_login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
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

            return result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            loggedin=result;
            verify();
        }
    }

}
