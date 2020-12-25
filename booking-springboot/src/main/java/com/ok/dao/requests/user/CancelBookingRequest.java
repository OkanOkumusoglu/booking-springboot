package com.ok.dao.requests.user;

public class CancelBookingRequest {

    private String userName;
    private String companyNameBranch;
    private String day;
    private String hour;
    private long cancelReservationAmount;

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

    public long getCancelReservationAmount() {
        return cancelReservationAmount;
    }

    public void setCancelReservationAmount(long cancelReservationAmount) {
        this.cancelReservationAmount = cancelReservationAmount;
    }
}
