package com.geekhive.studentsoft.beans.OrdersAr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersAr {
    @SerializedName("orders")
    @Expose
    List<String> ordersArray;

    public List<String> getOrdersArray() {
        return ordersArray;
    }

    public void setOrdersArray(List<String> ordersArray) {
        this.ordersArray = ordersArray;
    }
}
