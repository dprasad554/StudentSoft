
package com.geekhive.studentsoft.beans.getallbus;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("students")
    @Expose
    private List<Student> students = null;
    @SerializedName("stops")
    @Expose
    private List<Stop_> stops = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("bus_number")
    @Expose
    private long busNumber;
    @SerializedName("driver_details")
    @Expose
    private DriverDetails driverDetails;
    @SerializedName("starting_point")
    @Expose
    private StartingPoint startingPoint;
    @SerializedName("ending_point")
    @Expose
    private EndingPoint endingPoint;
    @SerializedName("capacity")
    @Expose
    private long capacity;
    @SerializedName("__v")
    @Expose
    private long v;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Stop_> getStops() {
        return stops;
    }

    public void setStops(List<Stop_> stops) {
        this.stops = stops;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(long busNumber) {
        this.busNumber = busNumber;
    }

    public DriverDetails getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverDetails driverDetails) {
        this.driverDetails = driverDetails;
    }

    public StartingPoint getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(StartingPoint startingPoint) {
        this.startingPoint = startingPoint;
    }

    public EndingPoint getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(EndingPoint endingPoint) {
        this.endingPoint = endingPoint;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

}
