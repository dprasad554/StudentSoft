
package com.geekhive.studentsoft.beans.addtocart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCart {

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
