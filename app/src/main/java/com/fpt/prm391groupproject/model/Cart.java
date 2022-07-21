package com.fpt.prm391groupproject.model;

public class Cart {
    private String ProductId;
    private String UserId;
    private String Image;
    private int Quantity;
    private int Price;
    private String ProductName;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
