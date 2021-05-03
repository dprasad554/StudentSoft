
package com.geekhive.studentsoft.beans.feestructure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("final_term_fees")
    @Expose
    private long finalTermFees;
    @SerializedName("tution_fees")
    @Expose
    private long tutionFees;
    @SerializedName("examination_fees")
    @Expose
    private long examinationFees;
    @SerializedName("practical_fees")
    @Expose
    private long practicalFees;
    @SerializedName("library_fees")
    @Expose
    private long libraryFees;
    @SerializedName("annual_maintenence_fees")
    @Expose
    private long annualMaintenenceFees;
    @SerializedName("admission_fees")
    @Expose
    private long admissionFees;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("__v")
    @Expose
    private long v;
    @SerializedName("total_fees")
    @Expose
    private long totalFees;
    @SerializedName("fees_paid")
    @Expose
    private long feesPaid;
    @SerializedName("fees_pending")
    @Expose
    private long feesPending;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public long getFinalTermFees() {
        return finalTermFees;
    }

    public void setFinalTermFees(long finalTermFees) {
        this.finalTermFees = finalTermFees;
    }

    public long getTutionFees() {
        return tutionFees;
    }

    public void setTutionFees(long tutionFees) {
        this.tutionFees = tutionFees;
    }

    public long getExaminationFees() {
        return examinationFees;
    }

    public void setExaminationFees(long examinationFees) {
        this.examinationFees = examinationFees;
    }

    public long getPracticalFees() {
        return practicalFees;
    }

    public void setPracticalFees(long practicalFees) {
        this.practicalFees = practicalFees;
    }

    public long getLibraryFees() {
        return libraryFees;
    }

    public void setLibraryFees(long libraryFees) {
        this.libraryFees = libraryFees;
    }

    public long getAnnualMaintenenceFees() {
        return annualMaintenenceFees;
    }

    public void setAnnualMaintenenceFees(long annualMaintenenceFees) {
        this.annualMaintenenceFees = annualMaintenenceFees;
    }

    public long getAdmissionFees() {
        return admissionFees;
    }

    public void setAdmissionFees(long admissionFees) {
        this.admissionFees = admissionFees;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    public long getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(long totalFees) {
        this.totalFees = totalFees;
    }

    public long getFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(long feesPaid) {
        this.feesPaid = feesPaid;
    }

    public long getFeesPending() {
        return feesPending;
    }

    public void setFeesPending(long feesPending) {
        this.feesPending = feesPending;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
