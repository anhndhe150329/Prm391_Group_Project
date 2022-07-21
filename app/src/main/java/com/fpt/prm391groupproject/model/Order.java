package com.fpt.prm391groupproject.model;

import java.util.Date;

public class Order {
    private String UserId;
    private String OrderId;
    private String OrderDate;

    public Order() {
    }

    public Order(String userId, String orderId, String orderDate) {
        UserId = userId;
        OrderId = orderId;
        OrderDate = orderDate;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }
}
