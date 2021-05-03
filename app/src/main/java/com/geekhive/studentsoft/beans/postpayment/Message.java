
package com.geekhive.studentsoft.beans.postpayment;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("order_ids")
    @Expose
    private List<Object> orderIds = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<Object> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Object> orderIds) {
        this.orderIds = orderIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
