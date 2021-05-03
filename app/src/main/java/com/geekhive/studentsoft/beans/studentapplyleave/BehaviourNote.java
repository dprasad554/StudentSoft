
package com.geekhive.studentsoft.beans.studentapplyleave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BehaviourNote {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("details")
    @Expose
    private String details;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
