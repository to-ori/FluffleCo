package com.example.theflufflecollaboration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayCommentOnPost extends AppCompatActivity {

    LocalUserDatabase localUserDatabase;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ReplyAdapter replyAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comment_on_post);
        localUserDatabase = new LocalUserDatabase(this);

        replyAdapter = new ReplyAdapter(this, R.layout.post_comment_row);
        listView= (ListView) findViewById(R.id.CommentsOnPost);

        listView.setAdapter(replyAdapter);
        json_string = getIntent().getExtras().getString("json_data");

        try{
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String userName, comment;

            while(count<jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                userName = JO.getString("username");
                comment = JO.getString("reply");

                ReplyToPost reply = new ReplyToPost(userName, comment);
                replyAdapter.add(reply);
                count++;
            }

            String productsFound=count +" replys to the post found";
            Toast.makeText(getApplicationContext(), productsFound, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }

    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    public void onAddComment(View view){
        startActivity(new Intent(this, AddComment.class));
    }
}
