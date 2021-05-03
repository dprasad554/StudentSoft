
package com.geekhive.studentsoft.beans.addonlinepayment;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateOnlinePayment {

    @SerializedName("person_id")
    @Expose
    private String personId;
    @SerializedName("person_type")
    @Expose
    private String personType;
    @SerializedName("order_ids")
    @Expose
    private List<String> orderIds = null;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;

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

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

}
