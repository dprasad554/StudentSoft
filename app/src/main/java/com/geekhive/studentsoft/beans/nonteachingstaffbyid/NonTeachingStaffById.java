
package com.geekhive.studentsoft.beans.nonteachingstaffbyid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonTeachingStaffById {

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
