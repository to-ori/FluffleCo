package com.example.theflufflecollaboration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 11486248 on 10/02/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VRESION = 2;
    private static final String DATABASE_NAME ="contacts.db";
    // User table details
    private static final String TABLE_NAME= "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASS = "pass";

    //Admin table details
    private static final String ADMIN_TABLE_NAME= "admin";
    private static final String ADMIN_COLUMN_ID = "a_id";
    private static final String ADMIN_COLUMN_NAME = "admin_name";
    private static final String ADMIN_COLUMN_USERNAME = "admin_username";
    private static final String ADMIN_COLUMN_EMAIL = "admin_email";
    private static final String ADMIN_COLUMN_PASS = "admin_pass";

    SQLiteDatabase db;

    //CREATE USER TABLE
    private static final String TABLE_CREATE="CREATE TABLE "+TABLE_NAME+"( "+COLUMN_ID+" INTEGER PRIMARY KEY NOT NULL , "
            +COLUMN_NAME+" TEXT NOT NULL, "
            + COLUMN_EMAIL+" TEXT NOT NULL, "
            +COLUMN_USERNAME+ " TEXT NOT NULL,  "
            +COLUMN_PASS+" TEXT NOT NULL );";

    //CREATE ADMIN TABLE
    private static final String ADMIN_TABLE_CREATE="CREATE TABLE "+ADMIN_TABLE_NAME+"( "+ADMIN_COLUMN_ID+" INTEGER PRIMARY KEY NOT NULL , "
            +ADMIN_COLUMN_NAME+" TEXT NOT NULL, "
            + ADMIN_COLUMN_USERNAME+ " TEXT NOT NULL,  "
            + ADMIN_COLUMN_EMAIL+" TEXT NOT NULL, "
            +ADMIN_COLUMN_PASS+" TEXT NOT NULL );";

    public DatabaseHelper(Context context){
        super(context ,DATABASE_NAME, null, DATABASE_VRESION);
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(ADMIN_TABLE_CREATE);
        this.db=db;
    }

    //upgrade user tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS"+ TABLE_NAME+";";
        db.execSQL(query);
        this.onCreate(db);

    }

    //insert contacts details into user table
    public void insertContact(Contact c)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //using a query to find out how many rows there are in the database and setting the id equal to this
        String query = "SELECT * FROM "+TABLE_NAME+";";
        Cursor curser = db.rawQuery(query, null);
        int count = curser.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_USERNAME, c.getUsername());
        values.put(COLUMN_PASS, c.getPass());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //insert contact details into admin table
    public void insertAdmin(Contact c)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //using a query to find out how many rows there are in the database and setting the id equal to this
        String query = "SELECT * FROM "+ADMIN_TABLE_NAME+";";
        Cursor curser = db.rawQuery(query, null);
        int count = curser.getCount();

        values.put(ADMIN_COLUMN_ID, count);
        values.put(ADMIN_COLUMN_NAME, c.getName());
        values.put(ADMIN_COLUMN_EMAIL, c.getEmail());
        values.put(ADMIN_COLUMN_USERNAME, c.getUsername());
        values.put(ADMIN_COLUMN_PASS, c.getPass());

        db.insert(ADMIN_TABLE_NAME, null, values);
        db.close();
    }

    //SEARCH USED TO SEARCH FOR A USERS PASSWORD IN A SYSTEM
    public String searchPass(String username)
    {
        db= this.getReadableDatabase();
        String query = "SELECT "+ COLUMN_USERNAME + ", "+ COLUMN_PASS+" FROM " + TABLE_NAME+";";
        Cursor curser = db.rawQuery(query, null);
        String a, b;
        b = "not found";

        //if (curser.moveToFirst())
        //{
        curser.moveToFirst();
            do {
                a = curser.getString(0);

                if(a.equals(username))
                {
                    b = curser.getString(1);
                    break;
                }

            } while(curser.moveToNext());
        //}
        return b;

    }

    //SEARCH USED TO SEARCH FOR A USERS PASSWORD IN A SYSTEM
    public String searchAdminPass(String username)
    {
        db= this.getReadableDatabase();
        String admin_query = "SELECT "+ ADMIN_COLUMN_USERNAME + ", "+ ADMIN_COLUMN_PASS+" FROM " + ADMIN_TABLE_NAME+";";
        Cursor curser = db.rawQuery(admin_query, null);
        String a, b;
        b = "not found";


        curser.moveToFirst();
        do {
            a = curser.getString(curser.getColumnIndex(ADMIN_COLUMN_USERNAME));

            if(a.equals(username))
            {
                b = curser.getString(curser.getColumnIndex(ADMIN_COLUMN_PASS));
                break;
            }

        } while(curser.moveToNext());

        return b;

    }
}
