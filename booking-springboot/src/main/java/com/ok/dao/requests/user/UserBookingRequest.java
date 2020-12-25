package com.ok.dao.requests.user;

public class UserBookingRequest {

    private String userName;
    private String companyNameBranch;
    private String day;
    private String hour;
    private long capacity;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyNameBranch() {
        return companyNameBranch;
    }

    public void setCompanyNameBranch(String companyNameBranch) {
        this.companyNameBranch = companyNameBranch;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }
}
