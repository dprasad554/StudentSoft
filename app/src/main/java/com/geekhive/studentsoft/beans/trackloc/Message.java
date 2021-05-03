
package com.geekhive.studentsoft.beans.trackloc;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("attendance")
    @Expose
    private List<Attendance> attendance = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("job_role")
    @Expose
    private String jobRole;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("joining_date")
    @Expose
    private String joiningDate;
    @SerializedName("permanent_address")
    @Expose
    private PermanentAddress permanentAddress;
    @SerializedName("temporary_address")
    @Expose
    private TemporaryAddress temporaryAddress;
    @SerializedName("shift_start_time")
    @Expose
    private String shiftStartTime;
    @SerializedName("shift_end_time")
    @Expose
    private String shiftEndTime;
    @SerializedName("bus_details")
    @Expose
    private BusDetails busDetails;
    @SerializedName("current_location")
    @Expose
    private CurrentLocation currentLocation;
    @SerializedName("firebase_id")
    @Expose
    private String firebaseId;
    @SerializedName("__v")
    @Expose
    private int v;

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public PermanentAddress getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(PermanentAddress permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public TemporaryAddress getTemporaryAddress() {
        return temporaryAddress;
    }

    public void setTemporaryAddress(TemporaryAddress temporaryAddress) {
        this.temporaryAddress = temporaryAddress;
    }

    public String getShiftStartTime() {
        return shiftStartTime;
    }

    public void setShiftStartTime(String shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }

    public String getShiftEndTime() {
        return shiftEndTime;
    }

    public void setShiftEndTime(String shiftEndTime) {
        this.shiftEndTime = shiftEndTime;
    }

    public BusDetails getBusDetails() {
        return busDetails;
    }

    public void setBusDetails(BusDetails busDetails) {
        this.busDetails = busDetails;
    }

    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

}
