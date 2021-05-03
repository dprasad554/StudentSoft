
package com.geekhive.studentsoft.beans.homebanner;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("class_hours")
    @Expose
    private List<ClassHour> classHours = null;
    @SerializedName("holidays")
    @Expose
    private List<Object> holidays = null;
    @SerializedName("media")
    @Expose
    private List<Object> media = null;
    @SerializedName("slider")
    @Expose
    private List<String> slider = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("website_url")
    @Expose
    private String websiteUrl;
    @SerializedName("__v")
    @Expose
    private long v;
    @SerializedName("is_active")
    @Expose
    private boolean isActive;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("teacher_hours")
    @Expose
    private TeacherHours teacherHours;
    @SerializedName("class_days")
    @Expose
    private ClassDays classDays;
    @SerializedName("teacher_leave_count")
    @Expose
    private long teacherLeaveCount;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("logo")
    @Expose
    private String logo;

    public List<ClassHour> getClassHours() {
        return classHours;
    }

    public void setClassHours(List<ClassHour> classHours) {
        this.classHours = classHours;
    }

    public List<Object> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Object> holidays) {
        this.holidays = holidays;
    }

    public List<Object> getMedia() {
        return media;
    }

    public void setMedia(List<Object> media) {
        this.media = media;
    }

    public List<String> getSlider() {
        return slider;
    }

    public void setSlider(List<String> slider) {
        this.slider = slider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public TeacherHours getTeacherHours() {
        return teacherHours;
    }

    public void setTeacherHours(TeacherHours teacherHours) {
        this.teacherHours = teacherHours;
    }

    public ClassDays getClassDays() {
        return classDays;
    }

    public void setClassDays(ClassDays classDays) {
        this.classDays = classDays;
    }

    public long getTeacherLeaveCount() {
        return teacherLeaveCount;
    }

    public void setTeacherLeaveCount(long teacherLeaveCount) {
        this.teacherLeaveCount = teacherLeaveCount;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
