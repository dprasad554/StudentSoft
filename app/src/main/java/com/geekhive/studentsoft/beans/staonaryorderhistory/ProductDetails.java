
package com.geekhive.studentsoft.beans.staonaryorderhistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetails {

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
    private List<String> mediaUrls = null;
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
    private long quantity;
    @SerializedName("price")
    @Expose
    private long price;
    @SerializedName("is_deleted")
    @Expose
    private boolean isDeleted;
    @SerializedName("__v")
    @Expose
    private long v;

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

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
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

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

}
