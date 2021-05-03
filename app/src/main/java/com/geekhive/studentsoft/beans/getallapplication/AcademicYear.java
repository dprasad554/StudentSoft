
package com.geekhive.studentsoft.beans.getallapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcademicYear {

    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("end")
    @Expose
    private Integer end;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

}
