package com.example.theflufflecollaboration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11486248 on 22/03/2017.
 */

class ReplyAdapter extends ArrayAdapter{

    private List list = new ArrayList();

    ReplyAdapter(Context context, int resource) {
        super(context, resource);
    }

    //adds ReplyToPost objects to the list
    public void add(ReplyToPost reply){
        super.add(reply);
        list.add(reply);
    }
    //gets the number of reviews in the list
    @Override
    public int getCount() {
        return list.size();
    }

    //returns the review at the postion passed into the method
    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    private static  class ReplyHolder{
        TextView tx_username, tx_reply;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row;
        row=convertView;
        ReplyAdapter.ReplyHolder replyHolder;

        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.post_comment_row, parent, false);
            replyHolder = new ReplyHolder();
            replyHolder.tx_username=(TextView)row.findViewById(R.id.tv_comment_username);
            replyHolder.tx_reply=(TextView)row.findViewById(R.id.tv_comment_reply);

            row.setTag(replyHolder);

        }
        else{
            replyHolder = (ReplyAdapter.ReplyHolder) row.getTag();
        }

        ReplyToPost reply = (ReplyToPost) this.getItem(position);

        if (reply != null) {
            replyHolder.tx_username.setText(reply.getReply_user_name());
            replyHolder.tx_reply.setText(reply.getReply_comment());
        }

        return row;
    }
}
