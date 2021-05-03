
package com.geekhive.studentsoft.beans.getallapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterviewDetail {

    @SerializedName("interview_id")
    @Expose
    private String interviewId;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("result")
    @Expose
    private String result;

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
