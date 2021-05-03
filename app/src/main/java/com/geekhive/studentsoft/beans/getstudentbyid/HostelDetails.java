
package com.geekhive.studentsoft.beans.getstudentbyid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HostelDetails {

    @SerializedName("hostel_id")
    @Expose
    private String hostelId;
    @SerializedName("hostel_name")
    @Expose
    private String hostelName;
    @SerializedName("room_number")
    @Expose
    private long roomNumber;

    public String getHostelId() {
        return hostelId;
    }

    public void setHostelId(String hostelId) {
        this.hostelId = hostelId;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public long getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(long roomNumber) {
        this.roomNumber = roomNumber;
    }

}
