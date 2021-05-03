
package com.geekhive.studentsoft.beans.sectionbyname;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectTeacher {

    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

}
