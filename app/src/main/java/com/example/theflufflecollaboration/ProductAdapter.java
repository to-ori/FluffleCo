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
 * Created by 11486248 on 11/03/2017.
 */

public class ProductAdapter extends ArrayAdapter {


    List list = new ArrayList();

    public ProductAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Product product) {
        super.add(product);
        list.add(product);
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
        ProductHolder productHolder;
        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout, parent, false);
            productHolder= new ProductHolder();
            productHolder.tx_name=(TextView)row.findViewById(R.id.tx_name);
            productHolder.tx_pet=(TextView)row.findViewById(R.id.tx_pet);
            productHolder.tx_product=(TextView)row.findViewById(R.id.tx_product);

            row.setTag(productHolder);

        }
        else{
            productHolder = (ProductHolder) row.getTag();
        }

        Product product = (Product) this.getItem(position);
        productHolder.tx_name.setText(product.getName());
        productHolder.tx_pet.setText(product.getPet_type());
        productHolder.tx_product.setText(product.getProduct_type());

        return row;
    }

    static class ProductHolder
    {
        TextView tx_name, tx_pet, tx_product;
    }
}

