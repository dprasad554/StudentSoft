
package com.geekhive.studentsoft.beans.getcartbyuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCartByUser {

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
