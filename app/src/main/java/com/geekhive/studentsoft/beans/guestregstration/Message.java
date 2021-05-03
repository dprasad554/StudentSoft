
package com.geekhive.studentsoft.beans.guestregstration;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("interview_details")
    @Expose
    private List<Object> interviewDetails = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("applicant_name")
    @Expose
    private String applicantName;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("date_of_birth_in_words")
    @Expose
    private String dateOfBirthInWords;
    @SerializedName("applied_for_class")
    @Expose
    private String appliedForClass;
    @SerializedName("age")
    @Expose
    private long age;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("caste")
    @Expose
    private String caste;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("residential_address")
    @Expose
    private String residentialAddress;
    @SerializedName("permanent_address")
    @Expose
    private String permanentAddress;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("academic_year")
    @Expose
    private AcademicYear academicYear;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("applied_by")
    @Expose
    private String appliedBy;
    @SerializedName("__v")
    @Expose
    private long v;

    public List<Object> getInterviewDetails() {
        return interviewDetails;
    }

    public void setInterviewDetails(List<Object> interviewDetails) {
        this.interviewDetails = interviewDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirthInWords() {
        return dateOfBirthInWords;
    }

    public void setDateOfBirthInWords(String dateOfBirthInWords) {
        this.dateOfBirthInWords = dateOfBirthInWords;
    }

    public String getAppliedForClass() {
        return appliedForClass;
    }

    public void setAppliedForClass(String appliedForClass) {
        this.appliedForClass = appliedForClass;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppliedBy() {
        return appliedBy;
    }

    public void setAppliedBy(String appliedBy) {
        this.appliedBy = appliedBy;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

}
