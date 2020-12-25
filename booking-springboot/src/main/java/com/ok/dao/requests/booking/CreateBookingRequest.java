package com.ok.dao.requests.booking;


public class CreateBookingRequest {

    private String companyNameBranch;
    private String day;


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
}
