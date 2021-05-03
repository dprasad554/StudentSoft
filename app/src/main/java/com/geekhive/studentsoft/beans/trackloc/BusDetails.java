
package com.geekhive.studentsoft.beans.trackloc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusDetails {

    @SerializedName("bus_number")
    @Expose
    private String busNumber;

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

}
