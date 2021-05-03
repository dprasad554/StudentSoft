
package com.geekhive.studentsoft.beans.getallbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stop_ {

    @SerializedName("stop_number")
    @Expose
    private String stopNumber;
    @SerializedName("stop_name")
    @Expose
    private String stopName;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("status")
    @Expose
    private long status;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

}
