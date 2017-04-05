package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class AddComment extends AppCompatActivity {

    LocalUserDatabase localUserDatabase;
    LocalPostDatabase localPostDatabase;
    TextView op_tv;
    EditText comment_et;
    String op_id, op_post, user_id, reply_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        localUserDatabase = new LocalUserDatabase(this);
        localPostDatabase = new LocalPostDatabase(this);
        op_tv = (TextView) findViewById(R.id.tv_OrigonalPost);
        Post post = localPostDatabase.getStoredPost();
        op_post = post.getPost_content();
        op_id = post.getPost_id();
        op_tv.setText(op_post);
        comment_et = (EditText) findViewById(R.id.et_Reply);

        Contact user = localUserDatabase.getLoggedInUser();
        user_id = user.id;

    }

    public void onSub(View view){
        reply_comment = comment_et.getText().toString();
        new BackgroundTask().execute();

    }
    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }

    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    //this class interacts with the PHP file that interacts with the server
    class BackgroundTask extends AsyncTask<Void, Void,String> {

        String result;
        String createReply_url;


        @Override
        protected void onPreExecute() {
            createReply_url ="http://danu6.it.nuigalway.ie/FluffelCo/AddReplytoPost.php";
            result = null;

        }

        @Override
        protected String doInBackground(Void... params) {

            URL url = null;

            try {

                //create a new url pass it the correct url address for the appropriate php page
                url = new URL(createReply_url);
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
                String post_data = URLEncoder.encode("op_id", "UTF-8") + "=" + URLEncoder.encode(op_id, "UTF-8") + "&"
                        + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("reply_comment", "UTF-8") + "=" + URLEncoder.encode(reply_comment, "UTF-8");


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
            startActivity(new Intent(AddComment.this, DisplayPost.class));
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }
}
