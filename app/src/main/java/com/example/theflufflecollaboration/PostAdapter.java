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
 * Created by 11486248 on 21/03/2017.
 */

public class PostAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public PostAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Post post){
        super.add(post);
        list.add(post);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        PostAdapter.PostHolder postHolder;

        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.post_row_layout, parent, false);
            postHolder= new PostAdapter.PostHolder();
            postHolder.tx_title=(TextView)row.findViewById(R.id.tv_title);
            postHolder.tx_user=(TextView)row.findViewById(R.id.tv_postedby);

            row.setTag(postHolder);

        }
        else{
            postHolder = (PostAdapter.PostHolder) row.getTag();
        }

        Post post = (Post) this.getItem(position);
        postHolder.tx_title.setText(post.getPost_titel());
        postHolder.tx_user.setText(post.getUser());

        return row;
    }

    static class PostHolder
    {
        TextView tx_title, tx_user;


    }
}
