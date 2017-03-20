package com.example.theflufflecollaboration;

/**
 * Created by 11486248 on 11/03/2017.
 */

public class Product {

    private String id, name, description, pet_type, product_type;

    public Product(String id, String name, String description, String pet_type, String product_type) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setPet_type(pet_type);
        this.setProduct_type(product_type);
    }

    public String getPet_type() {
        return pet_type;
    }

    public void setPet_type(String pet_type) {
        this.pet_type = pet_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
