package com.example.theflufflecollaboration;

/**
 * Created by 11486248 on 21/03/2017.
 */

public class Post {
    private String post_id, post_titel, post_content, user;

    public Post(String post_id, String post_titel,String post_content,String user){
        setPost_id(post_id);
        setPost_titel(post_titel);
        setPost_content(post_content);
        setUser(user);
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_titel() {
        return post_titel;
    }

    public void setPost_titel(String post_titel) {
        this.post_titel = post_titel;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
