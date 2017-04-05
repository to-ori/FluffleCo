package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CreatePost extends AppCompatActivity {

    LocalUserDatabase localUserDatabase;
    String p_title, p_content, post_user_id, json_string;
    EditText titleET, contentET;
    JSONObject jsonObject;
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        localUserDatabase = new LocalUserDatabase(this);
        //get the user id from the saved details of the logged in user in the localUserDatabase
        Contact contact = localUserDatabase.getLoggedInUser();
        post_user_id=contact.id;

        titleET = (EditText) findViewById(R.id.et_postTitle);
        contentET = (EditText) findViewById(R.id.et_comment);
    }

    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }
    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }


    public void onPostToCommunity(View view){
        p_title=titleET.getText().toString();
        p_content=contentET.getText().toString();
        if(p_title.equals("")){
            Toast.makeText(getApplicationContext(), "Please fill in title", Toast.LENGTH_LONG).show();
        }
        else if(p_content.equals("")){
            Toast.makeText(getApplicationContext(), "Please fill in content", Toast.LENGTH_LONG).show();
        }
        else {
            new BackgroundTask().execute();
        }
    }
    //this class interacts with the PHP file that interacts with the server
    class BackgroundTask extends AsyncTask<Void, Void,String> {

        String result;
        String createPost_url;


        @Override
        protected void onPreExecute() {
            createPost_url ="http://danu6.it.nuigalway.ie/FluffelCo/AddPost.php";
            result = null;

        }

        @Override
        protected String doInBackground(Void... params) {

            URL url = null;

            try {

                //create a new url pass it the correct url address for the appropriate php page
                url = new URL(createPost_url);
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
                String post_data = URLEncoder.encode("p_title", "UTF-8") + "=" + URLEncoder.encode(p_title, "UTF-8") + "&"
                        + URLEncoder.encode("p_content", "UTF-8") + "=" + URLEncoder.encode(p_content, "UTF-8") + "&"
                        + URLEncoder.encode("post_user_id", "UTF-8") + "=" + URLEncoder.encode(post_user_id, "UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                //iso-8859-1 is the type of data we are expecting
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

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //post toat to screen with result
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            new ShowPostsBackgroundTask().execute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    class ShowPostsBackgroundTask extends AsyncTask<Void, Void,String>
    {
        String result;
        String showPosts_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            showPosts_url="http://danu6.it.nuigalway.ie/FluffelCo/selectAllPosts.php";
            result = null;
        }

        @Override
        protected String doInBackground(Void... params) {

            URL url = null;
            try {

                url = new URL(showPosts_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


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
            json_string=result;
            checkString();

        }
    }

    private void checkString() {
        try{
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jsonArray.length()<1){
            Toast.makeText(getApplicationContext(), "No posts found", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "To see a post just click on it.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DisplayListOfPost.class);
            intent.putExtra("json_data",json_string);
            startActivity(intent );
        }
    }

}
