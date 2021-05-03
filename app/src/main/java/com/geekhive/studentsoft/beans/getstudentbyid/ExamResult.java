
package com.geekhive.studentsoft.beans.getstudentbyid;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamResult {

    @SerializedName("exam")
    @Expose
    private String exam;
    @SerializedName("results")
    @Expose
    private List<Result_> results = null;

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public List<Result_> getResults() {
        return results;
    }

    public void setResults(List<Result_> results) {
        this.results = results;
    }

}
