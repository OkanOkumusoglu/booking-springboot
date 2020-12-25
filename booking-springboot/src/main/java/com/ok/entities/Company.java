package com.ok.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {

    @Id
    @org.springframework.data.annotation.Id
    private String companyNameBranch;

    private String category;
    private String companyPassword;
    private String country;
    private String companyMail;
    private long companyPhone;
    private List<Booking> bookings=new ArrayList<>();

    public String getCategory() {
        return category;
    }


    public String getCompanyNameBranch() {
        return companyNameBranch;
    }

    public void setCompanyNameBranch(String companyNameBranch) {
        this.companyNameBranch = companyNameBranch;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @JsonIgnore
    @JsonProperty(value = "companyPassword")
    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public long getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(long companyPhone) {
        this.companyPhone = companyPhone;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
