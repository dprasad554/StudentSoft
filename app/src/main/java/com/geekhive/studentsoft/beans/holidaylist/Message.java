
package com.geekhive.studentsoft.beans.holidaylist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("reason")
    @Expose
    private String reason;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
