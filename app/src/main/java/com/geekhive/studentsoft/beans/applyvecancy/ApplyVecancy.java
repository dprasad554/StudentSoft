
package com.geekhive.studentsoft.beans.applyvecancy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplyVecancy {

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
    private String age;
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

    @SerializedName("applied_by")
    @Expose
    private String appliedBy;

    public String getAppliedBy() {
        return appliedBy;
    }

    public void setAppliedBy(String appliedBy) {
        this.appliedBy = appliedBy;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

}
