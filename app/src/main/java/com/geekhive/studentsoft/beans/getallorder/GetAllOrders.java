
package com.geekhive.studentsoft.beans.getallorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllOrders {

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
