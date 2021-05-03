
package com.geekhive.studentsoft.beans.teachertimetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Monday {

    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("subject")
    @Expose
    private String subject;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
