package com.example.theflufflecollaboration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddProduct extends AppCompatActivity  {
    String pname, description, pet_type, product_types;
    private static RadioGroup radoig_pet_type, radiog_product_type;
    private static RadioButton radio_pet, radio_product;
    private static Button button_sbm;
    EditText et_name, et_desc;
    LocalProductDatabase localPDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        onClickListenerButton();
        localPDB = new LocalProductDatabase(this);

        //set Edit text variable equal edittext fields in the xml page
        et_name= (EditText) findViewById(R.id.et_productname);
        et_desc= (EditText) findViewById(R.id.et_description);

    }

   public void onMainMenu(View view){
       startActivity(new Intent(this, OpenPage.class));
   }

    private void showProdcut() {
        Product Thisproduct = new Product("na", pname, description, pet_type, product_types);
        localPDB.storeProduct(Thisproduct);
        startActivity(new Intent(this, AdminDisplayProduct.class));
    }


    public void onClickListenerButton(){
        radoig_pet_type = (RadioGroup) findViewById(R.id.rg_petType);
        radiog_product_type = (RadioGroup) findViewById(R.id.rg_productType);
        button_sbm =(Button) findViewById(R.id.productSubmitbtn);
        button_sbm.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        int selected_id=radoig_pet_type.getCheckedRadioButtonId();
                        radio_pet=(RadioButton)findViewById(selected_id);
                        pet_type=radio_pet.getText().toString();
                        int selectedID = radiog_product_type.getCheckedRadioButtonId();
                        radio_product=(RadioButton) findViewById(selectedID);
                        product_types=radio_product.getText().toString();
                        pname=et_name.getText().toString();
                        description= et_desc.getText().toString();
                        new BackgroundTask().execute();
                    }
                }
        );

    }
    //this class interacts with the PHP file that interacts with the server
    class BackgroundTask extends AsyncTask<Void, Void,String> {

        String result;
        String submitProduct_url;


        @Override
        protected void onPreExecute() {
            submitProduct_url ="http://danu6.it.nuigalway.ie/FluffelCo/submitProduct.php";
            result = null;

        }

        @Override
        protected String doInBackground(Void... params) {

            URL url = null;

            try {

                //create a new url pass it the correct url address for the appropriate php page
                url = new URL(submitProduct_url);
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

                String post_data = URLEncoder.encode("pname", "UTF-8") + "=" + URLEncoder.encode(pname, "UTF-8") + "&"
                        + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8") + "&"
                        + URLEncoder.encode("pet_type", "UTF-8") + "=" + URLEncoder.encode(pet_type, "UTF-8") + "&"
                        + URLEncoder.encode("product_types", "UTF-8") + "=" + URLEncoder.encode(product_types, "UTF-8");


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

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            showProdcut();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }


}
