package com.example.theflufflecollaboration;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by 11486248 on 10/02/2017.
 */

public class DisplayInfo extends Activity {

    TextView tvname, tvemail, tvusername, tvpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_info);

        tvname= (TextView)findViewById(R.id.TVname);
        tvemail= (TextView)findViewById(R.id.TVemail);
        tvusername= (TextView)findViewById(R.id.TVusername);
        tvpassword= (TextView)findViewById(R.id.TVpassword);

        String username = getIntent().getStringExtra("Username");

        TextView tv = (TextView)findViewById(R.id.TVusername);
        tv.setText(username);
    }
}
