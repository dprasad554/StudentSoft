
package com.geekhive.studentsoft.beans.getstudentbyid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentClass {

    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

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
