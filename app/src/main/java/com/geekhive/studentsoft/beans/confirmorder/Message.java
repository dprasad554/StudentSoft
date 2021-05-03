
package com.geekhive.studentsoft.beans.confirmorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("person_id")
    @Expose
    private String personId;
    @SerializedName("person_type")
    @Expose
    private String personType;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("quantity")
    @Expose
    private long quantity;
    @SerializedName("product_price")
    @Expose
    private long productPrice;
    @SerializedName("total_product_price")
    @Expose
    private long totalProductPrice;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ordered_date")
    @Expose
    private String orderedDate;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("__v")
    @Expose
    private long v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public long getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(long totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

}
