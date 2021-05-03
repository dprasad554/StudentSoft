
package com.geekhive.studentsoft.beans.getallclasses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("sections")
    @Expose
    private List<Section> sections = null;
    @SerializedName("subjects")
    @Expose
    private List<String> subjects = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("syllabus")
    @Expose
    private String syllabus;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

}
