
package com.geekhive.studentsoft.beans.stationarycategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationaryCategory {

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
