
package com.geekhive.studentsoft.beans.getstudenttimetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStudentTimetable {

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
