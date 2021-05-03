
package com.geekhive.studentsoft.beans.notice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("notice_details")
    @Expose
    private String noticeDetails;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("__v")
    @Expose
    private long v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeDetails() {
        return noticeDetails;
    }

    public void setNoticeDetails(String noticeDetails) {
        this.noticeDetails = noticeDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

}
