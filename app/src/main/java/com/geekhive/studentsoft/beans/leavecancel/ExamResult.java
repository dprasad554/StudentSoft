
package com.geekhive.studentsoft.beans.leavecancel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamResult {

    @SerializedName("Mid Term 1")
    @Expose
    private List<MidTerm1> midTerm1 = null;

    public List<MidTerm1> getMidTerm1() {
        return midTerm1;
    }

    public void setMidTerm1(List<MidTerm1> midTerm1) {
        this.midTerm1 = midTerm1;
    }

}
