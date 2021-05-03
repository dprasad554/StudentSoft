
package com.geekhive.studentsoft.beans.getstudentbyid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentDetails {

    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("father_occupation")
    @Expose
    private String fatherOccupation;
    @SerializedName("father_contact")
    @Expose
    private String fatherContact;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
    @SerializedName("mother_occupation")
    @Expose
    private String motherOccupation;
    @SerializedName("mother_contact")
    @Expose
    private String motherContact;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherOccupation() {
        return fatherOccupation;
    }

    public void setFatherOccupation(String fatherOccupation) {
        this.fatherOccupation = fatherOccupation;
    }

    public String getFatherContact() {
        return fatherContact;
    }

    public void setFatherContact(String fatherContact) {
        this.fatherContact = fatherContact;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherOccupation() {
        return motherOccupation;
    }

    public void setMotherOccupation(String motherOccupation) {
        this.motherOccupation = motherOccupation;
    }

    public String getMotherContact() {
        return motherContact;
    }

    public void setMotherContact(String motherContact) {
        this.motherContact = motherContact;
    }

}
