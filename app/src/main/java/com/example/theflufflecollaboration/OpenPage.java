package com.example.theflufflecollaboration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import static android.content.ContentValues.TAG;
import static com.example.theflufflecollaboration.R.layout.open_page;

/**
 * Created by 11486248 on 19/02/2017.
 */

public class OpenPage extends Activity implements View.OnTouchListener {

    GestureDetector gestureDetector;
    LocalUserDatabase localUserDatabase;
    //open_page.setOnTouchListener(this);
    //take any layout on which you want your gesture listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(open_page);
        localUserDatabase= new LocalUserDatabase(this);
        TextView tv = (TextView)findViewById(R.id.WelcomeMessage);

        gestureDetector = new GestureDetector(this, new OnSwipeListener() {

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.left) {
                    if(localUserDatabase.getUserLoggedIn()) {
                        Intent i = new Intent(OpenPage.this, MainMenu.class);
                        startActivity(i);

                    }else{
                        Intent i = new Intent(OpenPage.this, UserLogin.class);
                        startActivity(i);
                    }
                    Log.d(TAG, "onSwipe: up");
                }

                if (direction == Direction.right) {
                    Intent j = new Intent(OpenPage.this, AdminLogIn.class);

                    startActivity(j);
                    Log.d(TAG, "onSwipe: right");
                }
                return true;
           }

        });

        tv.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch");
        gestureDetector.onTouchEvent(event);
        return true;
    }


}


