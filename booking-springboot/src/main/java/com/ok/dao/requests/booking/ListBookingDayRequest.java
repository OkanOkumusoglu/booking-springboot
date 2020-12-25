package com.ok.dao.requests.booking;

import com.ok.entities.BookingId;

public class ListBookingDayRequest {

    private String companyName;
    private String day;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
