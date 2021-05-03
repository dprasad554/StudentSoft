package com.geekhive.studentsoft.beans.applyleave;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplyLeave {

    @SerializedName("dates")
    @Expose
    private List<String> dates = null;
    @SerializedName("reason")
    @Expose
    private String reason;

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

}
