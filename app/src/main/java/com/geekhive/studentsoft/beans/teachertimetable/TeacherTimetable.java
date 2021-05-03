
package com.geekhive.studentsoft.beans.teachertimetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherTimetable {

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
