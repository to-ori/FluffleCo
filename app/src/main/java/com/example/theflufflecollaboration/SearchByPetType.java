package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class SearchByPetType extends AppCompatActivity {

    LocalUserDatabase localUserDatabase;
    String json_string, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_pet_type);
        localUserDatabase = new LocalUserDatabase(this);


    }
    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }

    public void checkString(){
        if(json_string==null){
          Toast.makeText(getApplicationContext(), "No products found", Toast.LENGTH_LONG).show();
        }else {

            Intent intent = new Intent(this, DisplayListView.class);
            intent.putExtra("json_data",json_string);
            startActivity(intent );
        }

    }


   public void onDog(View view){
       type ="dog";
       new SearchByPetType.BackgroundTask().execute(type);
   }

    public void onCat(View view){
        type = "cat";
        new SearchByPetType.BackgroundTask().execute(type);
    }

    public void onSmallAnimal(View view){
        type="small pets";
        new SearchByPetType.BackgroundTask().execute(type);

    }

    class BackgroundTask extends AsyncTask<String, Void,String>
    {
        String result;
        String product_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            product_url="http://danu6.it.nuigalway.ie/FluffelCo/searchbypet.php";
            result = null;
        }

        @Override
        protected String doInBackground(String... params) {


            String petType = params[0];
            URL url = null;


                try {

                    url = new URL(product_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("petType", "UTF-8") + "=" + URLEncoder.encode(petType, "UTF-8");

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
            json_string=result;
            checkString();

        }
    }

}
