
package com.geekhive.studentsoft.beans.applyleave;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplyLeaveStudent {

    @SerializedName("dates")
    @Expose
    private List<String> dates = null;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("media_url")
    @Expose
    private List<String> mediaUrl = null;

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(List<String> mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

}
