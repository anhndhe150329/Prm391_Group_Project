package com.fpt.prm391groupproject.model;

public class Wallet {
    private String walletId;
    private String userId;
    private int money;
    private int point;

    public Wallet() {
    }

    public Wallet(String userId, int money, int point) {
        this.userId = userId;
        this.money = money;
        this.point = point;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
