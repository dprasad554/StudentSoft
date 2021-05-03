
package com.geekhive.studentsoft.beans.stonaryprouct;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("color")
    @Expose
    private List<Object> color = null;
    @SerializedName("size")
    @Expose
    private List<Object> size = null;
    @SerializedName("offers")
    @Expose
    private List<Object> offers = null;
    @SerializedName("media_urls")
    @Expose
    private List<Object> mediaUrls = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("main_category")
    @Expose
    private String mainCategory;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<Object> getColor() {
        return color;
    }

    public void setColor(List<Object> color) {
        this.color = color;
    }

    public List<Object> getSize() {
        return size;
    }

    public void setSize(List<Object> size) {
        this.size = size;
    }

    public List<Object> getOffers() {
        return offers;
    }

    public void setOffers(List<Object> offers) {
        this.offers = offers;
    }

    public List<Object> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<Object> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
