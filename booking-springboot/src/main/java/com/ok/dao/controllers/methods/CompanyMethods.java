package com.ok.dao.controllers.methods;

import com.ok.dao.CompanyRepository;
import com.ok.dao.requests.company.CreateCompanyRequest;
import com.ok.dao.responses.company.CreateCompanyResponse;
import com.ok.entities.Company;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CompanyMethods {

    public static void createCompanyAction(Company company, CompanyRepository compRepo, CreateCompanyRequest request, CreateCompanyResponse response) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12

        company.setCompanyNameBranch(request.getCompanyNameBranch());

        String encodedPassword = encoder.encode(request.getCompanyPassword());
        company.setCompanyPassword(encodedPassword);

        company.setCategory(request.getCategory());
        company.setCountry(request.getCountry());
        company.setCompanyMail(request.getCompanyMail());
        company.setCompanyPhone(request.getCompanyPhone());
        compRepo.save(company);
        response.setSuccess(true);
        response.setMessage("Successful");
    }
}
