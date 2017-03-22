package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class DisplayPost extends AppCompatActivity {

    LocalUserDatabase localUserDatabase;
    LocalPostDatabase localPostDatabase;
    TextView title_tx, postedBy_tv, post_tv;
    String postId;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_post);
        localPostDatabase = new LocalPostDatabase(this);
        title_tx = (TextView) findViewById(R.id.tv_PostTitle);
        post_tv= (TextView) findViewById(R.id.tv_post);
        postedBy_tv= (TextView) findViewById(R.id.tv_postedBy);
        Post post= localPostDatabase.getStoredPost();
        String title = post.getPost_titel();
        title_tx.setText(title);
        String user =post.getUser() ;
        postedBy_tv.setText(user);
        String poststuff = post.getPost_content();
        post_tv.setText(poststuff);

        postId = post.getPost_id();
    }

    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }

    public void onViewComments(View view){
    new BackgroundTask().execute();
    }

    public void onComment(View view){
        startActivity(new Intent(this, AddComment.class));
    }


    //this class interacts with the PHP file that interacts with the server
    class BackgroundTask extends AsyncTask<Void, Void,String> {

        String result;
        String ReplystoPost_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            ReplystoPost_url ="http://danu6.it.nuigalway.ie/FluffelCo/ReplystoPost.php";
            result = null;

        }

        @Override
        protected String doInBackground(Void... params) {

            URL url = null;

            try {

                //create a new url pass it the correct url address for the appropriate php page
                url = new URL(ReplystoPost_url);
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
                String post_data = URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(postId, "UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                //iso-8859-1 is the type of data we are expecting
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                result = stringBuilder.toString().trim();

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
            json_string=result;
            checkString();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    public void checkString(){
        try{
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jsonArray.length()<1){
            Toast.makeText(getApplicationContext(), "There are no reply's to the post yet. To leave a reply click the add comment button", Toast.LENGTH_LONG).show();
        }else {
            Intent intent = new Intent(this, DisplayCommentOnPost.class);
            intent.putExtra("json_data",json_string);
            startActivity(intent );
        }
    }
}
