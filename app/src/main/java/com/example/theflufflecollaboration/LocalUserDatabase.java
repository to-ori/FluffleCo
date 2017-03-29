package com.example.theflufflecollaboration;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 11486248 on 20/03/2017.
 */

public class LocalUserDatabase {
    private static final String SP_NAME = "UserDetails";
    SharedPreferences localUserDatabase;

    public LocalUserDatabase(Context context)
    {
        localUserDatabase = context.getSharedPreferences(SP_NAME, 0);
         }
    //store a new user
    void storeData(Contact contact)
    {
        SharedPreferences.Editor spEditor = localUserDatabase.edit();
        spEditor.putString("id", contact.id);
        spEditor.putString("Name", contact.name);
        spEditor.putString("Email", contact.email);
        spEditor.putString("Username", contact.username);
        spEditor.putString("Password", contact.password);
        spEditor.commit();
    }

    //return the detail of the logge din user
    Contact getLoggedInUser()
    {
        String id= localUserDatabase.getString("id","");
        String name = localUserDatabase.getString("Name","");
        String email = localUserDatabase.getString("Email", "");
        String usernamne = localUserDatabase.getString("Username", "");
        String password = localUserDatabase.getString("Password","");
        return new Contact(id, name, email, usernamne,password);
    }

    //set the status of the user, ie logged in or out
    void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor = localUserDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    //check if a user is logged in or out
    boolean getUserLoggedIn()
    {
        return localUserDatabase.getBoolean("loggedIn", false);
    }

    //clear the user data stored
    public void clearData()
    {
        SharedPreferences.Editor spEditor = localUserDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}