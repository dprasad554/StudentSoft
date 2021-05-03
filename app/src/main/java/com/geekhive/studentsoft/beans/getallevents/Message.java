
package com.geekhive.studentsoft.beans.getallevents;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("media_urls")
    @Expose
    private List<Object> mediaUrls = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("event_details")
    @Expose
    private String eventDetails;
    @SerializedName("event_date")
    @Expose
    private String eventDate;
    @SerializedName("event_time")
    @Expose
    private String eventTime;
    @SerializedName("event_venue")
    @Expose
    private String eventVenue;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<Object> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<Object> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
