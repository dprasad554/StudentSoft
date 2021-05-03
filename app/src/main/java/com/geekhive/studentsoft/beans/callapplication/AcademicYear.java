
package com.geekhive.studentsoft.beans.callapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcademicYear {

    @SerializedName("start")
    @Expose
    private int start;
    @SerializedName("end")
    @Expose
    private int end;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}
