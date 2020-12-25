package com.ok.entities;

import javax.persistence.Embeddable;

@Embeddable
public class BookingId {

    private String companyNameBranchUpperCase;
    private String dayUpperCase;


    public BookingId(String companyNameBranchUpperCase, String dayUpperCase) {
        this.companyNameBranchUpperCase = companyNameBranchUpperCase;
        this.dayUpperCase = dayUpperCase;
    }

    public String getCompanyNameBranchUpperCase() {
        return companyNameBranchUpperCase;
    }

    public void setCompanyNameBranchUpperCase(String companyNameBranchUpperCase) {
        this.companyNameBranchUpperCase = companyNameBranchUpperCase;
    }

    public String getDayUpperCase() {
        return dayUpperCase;
    }

    public void setDayUpperCase(String dayUpperCase) {
        this.dayUpperCase = dayUpperCase;
    }
}
