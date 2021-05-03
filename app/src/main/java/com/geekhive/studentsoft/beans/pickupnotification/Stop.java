
package com.geekhive.studentsoft.beans.pickupnotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stop {

    @SerializedName("stop_number")
    @Expose
    private String stopNumber;
    @SerializedName("stop_name")
    @Expose
    private String stopName;

    public String getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(String stopNumber) {
        this.stopNumber = stopNumber;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

}
