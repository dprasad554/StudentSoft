
package com.geekhive.studentsoft.beans.studentapplyleave;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("attendance")
    @Expose
    private List<Attendance> attendance = null;
    @SerializedName("behaviour_note")
    @Expose
    private List<BehaviourNote> behaviourNote = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("admission_year")
    @Expose
    private AdmissionYear admissionYear;
    @SerializedName("current_address")
    @Expose
    private CurrentAddress currentAddress;
    @SerializedName("permanent_address")
    @Expose
    private PermanentAddress permanentAddress;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("current_class")
    @Expose
    private CurrentClass currentClass;
    @SerializedName("exam_result")
    @Expose
    private ExamResult examResult;

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public List<BehaviourNote> getBehaviourNote() {
        return behaviourNote;
    }

    public void setBehaviourNote(List<BehaviourNote> behaviourNote) {
        this.behaviourNote = behaviourNote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public AdmissionYear getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(AdmissionYear admissionYear) {
        this.admissionYear = admissionYear;
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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public CurrentClass getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(CurrentClass currentClass) {
        this.currentClass = currentClass;
    }

    public ExamResult getExamResult() {
        return examResult;
    }

    public void setExamResult(ExamResult examResult) {
        this.examResult = examResult;
    }

}
