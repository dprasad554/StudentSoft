
package com.geekhive.studentsoft.beans.getstudentbyid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusDetails {

    @SerializedName("bus_number")
    @Expose
    private long busNumber;
    @SerializedName("stop")
    @Expose
    private Stop stop;

    public long getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(long busNumber) {
        this.busNumber = busNumber;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

}
