
package com.geekhive.studentsoft.beans.stationarycategory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("sex")
    @Expose
    private List<Object> sex = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sub_of")
    @Expose
    private String subOf;
    @SerializedName("media_url")
    @Expose
    private String mediaUrl;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<Object> getSex() {
        return sex;
    }

    public void setSex(List<Object> sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubOf() {
        return subOf;
    }

    public void setSubOf(String subOf) {
        this.subOf = subOf;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
