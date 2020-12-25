package com.ok.dao.requests.booking;

public class CancelBookingHourRequest {

    private String companyNameBranch;
    private String day;
    private String hour;

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
}
