
package com.geekhive.studentsoft.beans.teachertimetable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timetable {

    @SerializedName("Monday")
    @Expose
    private List<Monday> monday = null;
    @SerializedName("Tuesday")
    @Expose
    private List<Tuesday> tuesday = null;
    @SerializedName("Wednesday")
    @Expose
    private List<Wednesday> wednesday = null;
    @SerializedName("Thursday")
    @Expose
    private List<Thursday> thursday = null;
    @SerializedName("Friday")
    @Expose
    private List<Friday> friday = null;
    @SerializedName("Saturday")
    @Expose
    private List<Object> saturday = null;
    @SerializedName("Sunday")
    @Expose
    private List<Object> sunday = null;

    public List<Monday> getMonday() {
        return monday;
    }

    public void setMonday(List<Monday> monday) {
        this.monday = monday;
    }

    public List<Tuesday> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<Tuesday> tuesday) {
        this.tuesday = tuesday;
    }

    public List<Wednesday> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<Wednesday> wednesday) {
        this.wednesday = wednesday;
    }

    public List<Thursday> getThursday() {
        return thursday;
    }

    public void setThursday(List<Thursday> thursday) {
        this.thursday = thursday;
    }

    public List<Friday> getFriday() {
        return friday;
    }

    public void setFriday(List<Friday> friday) {
        this.friday = friday;
    }

    public List<Object> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<Object> saturday) {
        this.saturday = saturday;
    }

    public List<Object> getSunday() {
        return sunday;
    }

    public void setSunday(List<Object> sunday) {
        this.sunday = sunday;
    }

}
