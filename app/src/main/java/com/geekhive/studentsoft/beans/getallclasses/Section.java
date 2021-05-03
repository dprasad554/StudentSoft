
package com.geekhive.studentsoft.beans.getallclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Section {

    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("class_teacher")
    @Expose
    private String classTeacher;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

}
