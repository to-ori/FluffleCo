    package com.example.theflufflecollaboration;

    /**
     * Created by 11486248 on 10/02/2017.
     */

    public class Contact {


        String id, name, email, username, password;

      public Contact(String id, String name, String email, String username, String password)
      {
          this.id=id;
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

