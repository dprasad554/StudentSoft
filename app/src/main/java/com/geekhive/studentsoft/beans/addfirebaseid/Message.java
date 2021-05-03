
package com.geekhive.studentsoft.beans.addfirebaseid;

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
    @SerializedName("assigned_classes")
    @Expose
    private int assignedClasses;
    @SerializedName("total_leave_count")
    @Expose
    private int totalLeaveCount;
    @SerializedName("leave_applied")
    @Expose
    private int leaveApplied;
    @SerializedName("__v")
    @Expose
    private int v;
    @SerializedName("salary")
    @Expose
    private Object salary;
    @SerializedName("blood_group")
    @Expose
    private Object bloodGroup;
    @SerializedName("emergency_contact_number")
    @Expose
    private Object emergencyContactNumber;
    @SerializedName("firebase_id")
    @Expose
    private String firebaseId;

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

    public int getAssignedClasses() {
        return assignedClasses;
    }

    public void setAssignedClasses(int assignedClasses) {
        this.assignedClasses = assignedClasses;
    }

    public int getTotalLeaveCount() {
        return totalLeaveCount;
    }

    public void setTotalLeaveCount(int totalLeaveCount) {
        this.totalLeaveCount = totalLeaveCount;
    }

    public int getLeaveApplied() {
        return leaveApplied;
    }

    public void setLeaveApplied(int leaveApplied) {
        this.leaveApplied = leaveApplied;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public Object getSalary() {
        return salary;
    }

    public void setSalary(Object salary) {
        this.salary = salary;
    }

    public Object getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(Object bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Object getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(Object emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

}
