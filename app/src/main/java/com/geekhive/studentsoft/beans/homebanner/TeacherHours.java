
package com.geekhive.studentsoft.beans.homebanner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherHours {

    @SerializedName("total_classes")
    @Expose
    private long totalClasses;
    @SerializedName("work_classes")
    @Expose
    private long workClasses;
    @SerializedName("other_classes")
    @Expose
    private long otherClasses;

    public long getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(long totalClasses) {
        this.totalClasses = totalClasses;
    }

    public long getWorkClasses() {
        return workClasses;
    }

    public void setWorkClasses(long workClasses) {
        this.workClasses = workClasses;
    }

    public long getOtherClasses() {
        return otherClasses;
    }

    public void setOtherClasses(long otherClasses) {
        this.otherClasses = otherClasses;
    }

}
