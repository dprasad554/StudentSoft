
package com.geekhive.studentsoft.beans.addtocart;

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
    private Integer quantity;
    @SerializedName("product_price")
    @Expose
    private Integer productPrice;
    @SerializedName("total_product_price")
    @Expose
    private Integer totalProductPrice;
    @SerializedName("__v")
    @Expose
    private Integer v;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(Integer totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
