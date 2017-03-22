package com.example.theflufflecollaboration;

class ReplyToPost  {

   private String reply_user_name, reply_comment;

    ReplyToPost(String userName, String comment){
        setReply_user_name(userName);
        setReply_comment(comment);
    }

    String getReply_user_name() {
        return reply_user_name;
    }

    private void setReply_user_name(String reply_user_name) {
        this.reply_user_name = reply_user_name;
    }

    String getReply_comment() {
        return reply_comment;
    }

    private void setReply_comment(String reply_comment) {
        this.reply_comment = reply_comment;
    }
}
