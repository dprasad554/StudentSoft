
package com.geekhive.studentsoft.beans.eventregistration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterationDetail {

    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

}
