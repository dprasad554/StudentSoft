
package com.geekhive.studentsoft.beans.teacherbyid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassesAlloted {

    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;

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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
