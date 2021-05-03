
package com.geekhive.studentsoft.beans.forgetpassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForGetPassword {

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
