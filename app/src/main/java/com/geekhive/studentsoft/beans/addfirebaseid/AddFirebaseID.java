
package com.geekhive.studentsoft.beans.addfirebaseid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFirebaseID {

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
