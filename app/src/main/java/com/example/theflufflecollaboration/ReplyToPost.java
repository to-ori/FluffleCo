package com.example.theflufflecollaboration;

public class ReplyToPost  {

   private String reply_user_name, reply_comment;

    public ReplyToPost(String userName, String comment){
        setReply_user_name(userName);
        setReply_comment(comment);
    }

    public String getReply_user_name() {
        return reply_user_name;
    }

    public void setReply_user_name(String reply_user_name) {
        this.reply_user_name = reply_user_name;
    }

    public String getReply_comment() {
        return reply_comment;
    }

    public void setReply_comment(String reply_comment) {
        this.reply_comment = reply_comment;
    }
}
