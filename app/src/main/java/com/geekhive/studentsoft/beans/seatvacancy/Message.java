
package com.geekhive.studentsoft.beans.seatvacancy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("total_seats")
    @Expose
    private Integer totalSeats;
    @SerializedName("seats_available")
    @Expose
    private Integer seatsAvailable;
    @SerializedName("start_application")
    @Expose
    private String startApplication;
    @SerializedName("end_application")
    @Expose
    private String endApplication;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getStartApplication() {
        return startApplication;
    }

    public void setStartApplication(String startApplication) {
        this.startApplication = startApplication;
    }

    public String getEndApplication() {
        return endApplication;
    }

    public void setEndApplication(String endApplication) {
        this.endApplication = endApplication;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
