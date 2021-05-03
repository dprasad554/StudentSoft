
package com.geekhive.studentsoft.beans.studentapplyleave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MidTerm1 {

    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("mark")
    @Expose
    private String mark;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

}
