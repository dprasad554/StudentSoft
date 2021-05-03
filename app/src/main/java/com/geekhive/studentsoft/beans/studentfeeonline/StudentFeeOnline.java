
package com.geekhive.studentsoft.beans.studentfeeonline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentFeeOnline {

    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
