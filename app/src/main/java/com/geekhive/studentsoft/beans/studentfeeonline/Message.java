
package com.geekhive.studentsoft.beans.studentfeeonline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("student_first_name")
    @Expose
    private String studentFirstName;
    @SerializedName("student_last_name")
    @Expose
    private String studentLastName;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("parent_name")
    @Expose
    private String parentName;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("fees_paid")
    @Expose
    private long feesPaid;
    @SerializedName("fees_pending")
    @Expose
    private long feesPending;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("is_pending")
    @Expose
    private boolean isPending;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("__v")
    @Expose
    private long v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isIsPending() {
        return isPending;
    }

    public void setIsPending(boolean isPending) {
        this.isPending = isPending;
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

}
