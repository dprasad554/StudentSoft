
package com.geekhive.studentsoft.beans.getstudentbyid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result_ {

    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("mark")
    @Expose
    private long mark;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public long getMark() {
        return mark;
    }

    public void setMark(long mark) {
        this.mark = mark;
    }

}
