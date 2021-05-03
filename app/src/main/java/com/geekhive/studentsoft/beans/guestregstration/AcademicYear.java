
package com.geekhive.studentsoft.beans.guestregstration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcademicYear {

    @SerializedName("start")
    @Expose
    private long start;
    @SerializedName("end")
    @Expose
    private long end;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

}
