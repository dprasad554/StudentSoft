package com.geekhive.studentsoft.beans.leaveapply;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dates {

    @SerializedName("dates")
    @Expose
    private List<String> dates = null;

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

}