    package com.example.theflufflecollaboration;

    /**
     * Created by 11486248 on 10/02/2017.
     */

    public class Contact {


        String name, email, username, password;

      public Contact(String name, String email, String username, String password)
      {
          this.name= name;
          this.email = email;
          this.username = username;
          this.password = password;
      }

       public Contact(String username, String password)
       {
           this.username= username;
           this.password = password;
       }

    }

