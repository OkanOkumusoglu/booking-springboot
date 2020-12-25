package com.ok.dao.requests.company;

public class CreateCompanyRequest {

    private String companyNameBranch;
    private String companyPassword;
    private String category;
    private String country;
    private String companyMail;
    private long companyPhone;


    public String getCompanyNameBranch() {
        return companyNameBranch;
    }

    public void setCompanyNameBranch(String companyNameBranch) {
        this.companyNameBranch = companyNameBranch;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}

