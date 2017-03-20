package com.example.theflufflecollaboration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddProduct extends AppCompatActivity  {
    String pname, description, pet_type, product_types;
    private static RadioGroup radoig_pet_type, radiog_product_type;
    private static RadioButton radio_pet, radio_product;
    private static Button button_sbm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        onClickListenerButton();

    }

    public void onClickListenerButton(){
        radoig_pet_type = (RadioGroup) findViewById(R.id.rg_petType);
        radiog_product_type = (RadioGroup) findViewById(R.id.rg_productType);
        button_sbm =(Button) findViewById(R.id.productSubmitbtn);
        button_sbm.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        int selected_id=radoig_pet_type.getCheckedRadioButtonId();
                        radio_pet=(RadioButton)findViewById(selected_id);
                        pet_type=radio_pet.getText().toString();
                        int selectedID = radiog_product_type.getCheckedRadioButtonId();
                        radio_product=(RadioButton) findViewById(selectedID);
                        product_types=radio_product.getText().toString();
                    }
                }
        );

    }


}
