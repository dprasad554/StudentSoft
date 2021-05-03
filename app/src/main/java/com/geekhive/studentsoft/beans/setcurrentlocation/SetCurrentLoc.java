
package com.geekhive.studentsoft.beans.setcurrentlocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetCurrentLoc {

    @SerializedName("current_location")
    @Expose
    private CurrentLocation currentLocation;

    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

}
