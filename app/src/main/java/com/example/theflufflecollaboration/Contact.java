package com.example.theflufflecollaboration;

/**
 * Created by 11486248 on 10/02/2017.
 */

public class Contact {

   private int _id;
    private String name, email, username, pass;

    public int get_id() {

        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

