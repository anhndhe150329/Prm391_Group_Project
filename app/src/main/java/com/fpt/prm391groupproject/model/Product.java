package com.fpt.prm391groupproject.model;

public class Product {
    private String id;
    private String productName;
    private int price;
    private int quantity;
    private String image;

    public Product() {
    }

    public Product(String id, String productName, int price, int quantity, String image) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public Product(String productName, int price, int quantity,  String image) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
