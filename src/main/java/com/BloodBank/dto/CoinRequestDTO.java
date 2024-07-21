package com.BloodBank.dto;

import com.BloodBank.Model.UserModel;

import java.util.Date;

public class CoinRequestDTO {
    private UserModel userId;
    private  String coinType;
    private int amount;
    private int coinRequestCount;

    public int getCoinRequestCount() {
        return coinRequestCount;
    }

    public void setCoinRequestCount(int coinRequestCount) {
        this.coinRequestCount = coinRequestCount;
    }

    public UserModel getUserId() {
        return userId;
    }

    public void setUserId(UserModel userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }
}
