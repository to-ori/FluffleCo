package com.example.theflufflecollaboration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListOfPost extends AppCompatActivity {
    LocalPostDatabase localPostDatabase;
    LocalUserDatabase localUserDatabase;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    PostAdapter postAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_of_post);
        localPostDatabase = new LocalPostDatabase(this);
        localUserDatabase= new LocalUserDatabase(this);
        postAdapter = new PostAdapter(this, R.layout.post_row_layout);
        listView =(ListView) findViewById(R.id.postlistview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post selectedPost = (Post) listView.getItemAtPosition(position);
                localPostDatabase.storePost(selectedPost);
                Intent i = new Intent (DisplayListOfPost.this, DisplayPosts.class );
                startActivity(i);
            }});
        listView.setAdapter(postAdapter);
        json_string =getIntent().getExtras().getString("json_data");



            json_string = getIntent().getExtras().getString("json_data");
            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String postid, postTitle, postContent, postUser;

                while(count<jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    postid = JO.getString("post_id");
                    postTitle=JO.getString("post_title");
                    postContent=JO.getString("post_content");
                    postUser=JO.getString("post_username");

                    Post post = new Post(postid, postTitle, postContent,postUser);
                    postAdapter.add(post);
                    count++;
                }
                String productsFound=count +" posts found";
                Toast.makeText(getApplicationContext(), productsFound, Toast.LENGTH_LONG).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }

    }


    public void onMainMenu(View view){
        startActivity(new Intent(this, MainMenu.class));
    }

    public void onLogout(View view){
        localUserDatabase.clearData();
        startActivity(new Intent(this, OpenPage.class));
    }
}
