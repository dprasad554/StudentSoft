
package com.geekhive.studentsoft.beans.callapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallApplication {

    @SerializedName("academic_year")
    @Expose
    private AcademicYear academicYear;
    @SerializedName("applied_for_class")
    @Expose
    private String appliedForClass;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("applied_by")
    @Expose
    private String appliedBy;

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public String getAppliedForClass() {
        return appliedForClass;
    }

    public void setAppliedForClass(String appliedForClass) {
        this.appliedForClass = appliedForClass;
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

}
