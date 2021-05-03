
package com.geekhive.studentsoft.beans.homework;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Homework_ {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("submission_date")
    @Expose
    private String submissionDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

}
