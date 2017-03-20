package com.example.theflufflecollaboration;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 11486248 on 03/03/2017.
 */

public class LocalProductDatabase {


    public static final String SP_NAME2 = "ProductDetails";

    SharedPreferences localProductDatabase;

    public LocalProductDatabase(Context context)
    {
        localProductDatabase=context.getSharedPreferences(SP_NAME2,0);
    }

    //store a new product
    public void storeProduct(Product product){
        SharedPreferences.Editor spEditor = localProductDatabase.edit();

        spEditor.putString("id", product.getId());
        spEditor.putString("name", product.getName());
        spEditor.putString("description", product.getDescription());
        spEditor.putString("pet_type", product.getPet_type());
        spEditor.putString("product_type", product.getProduct_type());
        spEditor.commit();
    }

    //get the details of the product stored
    public Product getProductDetails(){
        String id= localProductDatabase.getString("id","");
        String name= localProductDatabase.getString("name", "");
        String description= localProductDatabase.getString("description","");
        String pet_type= localProductDatabase.getString("pet_type","");
        String product_type = localProductDatabase.getString("product_type","");
        Product product =new Product(id, name, description, pet_type, product_type);
        return product;
    }

    //clear the stored product details
    public void clearProductData()
    {
        SharedPreferences.Editor spEditor = localProductDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }


}
