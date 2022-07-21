package com.fpt.prm391groupproject.model;

public class OrderDetail {
    private String OrderId;
    private String ProductId;
    private int Quantity;
    private int Price;
    private String Image;
    private String ProductName;

    public OrderDetail() {
    }

    public OrderDetail(String orderId, String productId, int quantity, int price, String image, String productName) {
        OrderId = orderId;
        ProductId = productId;
        Quantity = quantity;
        Price = price;
        Image = image;
        ProductName = productName;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
}
