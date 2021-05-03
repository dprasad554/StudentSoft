
package com.geekhive.studentsoft.beans.teacherpresentattendance;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("classes_allowed")
    @Expose
    private List<String> classesAllowed = null;
    @SerializedName("specialization")
    @Expose
    private List<String> specialization = null;
    @SerializedName("classes_alloted")
    @Expose
    private List<ClassesAlloted> classesAlloted = null;
    @SerializedName("attendance")
    @Expose
    private List<Attendance> attendance = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("date_of_joining")
    @Expose
    private String dateOfJoining;
    @SerializedName("current_address")
    @Expose
    private CurrentAddress currentAddress;
    @SerializedName("permanent_address")
    @Expose
    private PermanentAddress permanentAddress;
    @SerializedName("class_teacher_of")
    @Expose
    private ClassTeacherOf classTeacherOf;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("assigned_classes")
    @Expose
    private Object assignedClasses;
    @SerializedName("attendance_present")
    @Expose
    private List<String> attendancePresent = null;
    @SerializedName("attendance_absent")
    @Expose
    private List<String> attendanceAbsent = null;
    @SerializedName("total_leave_count")
    @Expose
    private Integer totalLeaveCount;
    @SerializedName("leave_applied")
    @Expose
    private Integer leaveApplied;

    public List<String> getClassesAllowed() {
        return classesAllowed;
    }

    public void setClassesAllowed(List<String> classesAllowed) {
        this.classesAllowed = classesAllowed;
    }

    public List<String> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(List<String> specialization) {
        this.specialization = specialization;
    }

    public List<ClassesAlloted> getClassesAlloted() {
        return classesAlloted;
    }

    public void setClassesAlloted(List<ClassesAlloted> classesAlloted) {
        this.classesAlloted = classesAlloted;
    }

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public CurrentAddress getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(CurrentAddress currentAddress) {
        this.currentAddress = currentAddress;
    }

    public PermanentAddress getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(PermanentAddress permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public ClassTeacherOf getClassTeacherOf() {
        return classTeacherOf;
    }

    public void setClassTeacherOf(ClassTeacherOf classTeacherOf) {
        this.classTeacherOf = classTeacherOf;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Object getAssignedClasses() {
        return assignedClasses;
    }

    public void setAssignedClasses(Object assignedClasses) {
        this.assignedClasses = assignedClasses;
    }

    public List<String> getAttendancePresent() {
        return attendancePresent;
    }

    public void setAttendancePresent(List<String> attendancePresent) {
        this.attendancePresent = attendancePresent;
    }

    public List<String> getAttendanceAbsent() {
        return attendanceAbsent;
    }

    public void setAttendanceAbsent(List<String> attendanceAbsent) {
        this.attendanceAbsent = attendanceAbsent;
    }

    public Integer getTotalLeaveCount() {
        return totalLeaveCount;
    }

    public void setTotalLeaveCount(Integer totalLeaveCount) {
        this.totalLeaveCount = totalLeaveCount;
    }

    public Integer getLeaveApplied() {
        return leaveApplied;
    }

    public void setLeaveApplied(Integer leaveApplied) {
        this.leaveApplied = leaveApplied;
    }

}
