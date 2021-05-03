
package com.geekhive.studentsoft.beans.getstudentbyid;

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
    @SerializedName("exam_result")
    @Expose
    private List<ExamResult> examResult = null;
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
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("admission_year")
    @Expose
    private AdmissionYear admissionYear;
    @SerializedName("current_address")
    @Expose
    private CurrentAddress currentAddress;
    @SerializedName("permanent_address")
    @Expose
    private PermanentAddress permanentAddress;
    @SerializedName("current_class")
    @Expose
    private CurrentClass currentClass;
    @SerializedName("parent_details")
    @Expose
    private ParentDetails parentDetails;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("emergency_contact_number")
    @Expose
    private String emergencyContactNumber;
    @SerializedName("firebase_id")
    @Expose
    private String firebaseId;
    @SerializedName("__v")
    @Expose
    private long v;
    @SerializedName("bus_details")
    @Expose
    private BusDetails busDetails;

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

    public List<ExamResult> getExamResult() {
        return examResult;
    }

    public void setExamResult(List<ExamResult> examResult) {
        this.examResult = examResult;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public CurrentClass getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(CurrentClass currentClass) {
        this.currentClass = currentClass;
    }

    public ParentDetails getParentDetails() {
        return parentDetails;
    }

    public void setParentDetails(ParentDetails parentDetails) {
        this.parentDetails = parentDetails;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    public BusDetails getBusDetails() {
        return busDetails;
    }

    public void setBusDetails(BusDetails busDetails) {
        this.busDetails = busDetails;
    }

}
