
package com.geekhive.studentsoft.beans.sectionbyname;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("subject_teacher")
    @Expose
    private List<SubjectTeacher> subjectTeacher = null;
    @SerializedName("students")
    @Expose
    private List<Student> students = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("total_students")
    @Expose
    private Integer totalStudents;
    @SerializedName("class_teacher_id")
    @Expose
    private String classTeacherId;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("class_teacher_name")
    @Expose
    private Object classTeacherName;

    public List<SubjectTeacher> getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(List<SubjectTeacher> subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getClassTeacherId() {
        return classTeacherId;
    }

    public void setClassTeacherId(String classTeacherId) {
        this.classTeacherId = classTeacherId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Object getClassTeacherName() {
        return classTeacherName;
    }

    public void setClassTeacherName(Object classTeacherName) {
        this.classTeacherName = classTeacherName;
    }

}
