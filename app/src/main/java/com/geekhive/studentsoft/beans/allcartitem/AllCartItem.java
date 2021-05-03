
package com.geekhive.studentsoft.beans.allcartitem;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllCartItem {

    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}
