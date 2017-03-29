package com.example.theflufflecollaboration;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 11486248 on 21/03/2017.
 */

class LocalPostDatabase {
    private static final String SP_NAME = "PostDetails";

    private SharedPreferences localPostDatabase;
    LocalPostDatabase(Context context){
        localPostDatabase=context.getSharedPreferences(SP_NAME,0);
    }

    //store a post
    void storePost(Post post){
        SharedPreferences.Editor spEditor = localPostDatabase.edit();
        spEditor.putString("id",post.getPost_id());
        spEditor.putString("title",post.getPost_titel());
        spEditor.putString("content",post.getPost_content());
        spEditor.putString("user",post.getUser());
        spEditor.commit();
    }

    //this method returns the details stored in the form of a post object
    Post getStoredPost(){
        String id, titel, content, userName;
        //here the first string is the key for the vaule we want to get back
        //the second string is the what to use if nothing is returned
        id= localPostDatabase.getString("id","");
        titel=localPostDatabase.getString("title","");
        content = localPostDatabase.getString("content","");
        userName=localPostDatabase.getString("user","");
        Post post = new Post(id, titel,content,userName);
        return  post;

    }

    //finall this method is used to clear the details currently stored
    public void clearPostData(){
        SharedPreferences.Editor spEditor = localPostDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
