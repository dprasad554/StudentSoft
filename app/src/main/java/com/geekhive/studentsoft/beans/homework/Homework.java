
package com.geekhive.studentsoft.beans.homework;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Homework {

    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("homework")
    @Expose
    private Homework_ homework;

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Homework_ getHomework() {
        return homework;
    }

    public void setHomework(Homework_ homework) {
        this.homework = homework;
    }

}
