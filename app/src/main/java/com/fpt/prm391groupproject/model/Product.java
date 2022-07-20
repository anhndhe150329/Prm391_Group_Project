package com.fpt.prm391groupproject.model;

public class Product {
    private String id;
    private String productName;
    private int price;
    private int quantity;
    private String image;
    private String categoryName;
    private String description;
    private boolean favourite;

    public Product() {
    }

    public Product(String productName, int price, int quantity, String image, String categoryName, String description, boolean favourite) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.categoryName = categoryName;
        this.description = description;
        this.favourite = favourite;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
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
