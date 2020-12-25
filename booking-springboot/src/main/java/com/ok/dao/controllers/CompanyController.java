package com.ok.dao.controllers;

import com.ok.ValidationChecks;
import com.ok.dao.CompanyRepository;
import com.ok.dao.controllers.methods.CompanyMethods;
import com.ok.dao.requests.company.CompanyListRequest;
import com.ok.dao.requests.company.CompanyLoginRequest;
import com.ok.dao.requests.company.CreateCompanyRequest;
import com.ok.dao.responses.GenericResponse;
import com.ok.dao.responses.company.CompanyListResponse;
import com.ok.dao.responses.company.CreateCompanyResponse;
import com.ok.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {


    @Autowired
    CompanyRepository compRepo;

    @PostMapping("/list")
    public CompanyListResponse listCompanies(@RequestBody CompanyListRequest request) {
        CompanyListResponse response = new CompanyListResponse();
        response.setCompanies(compRepo.findCompaniesByRegexpName(request.getCompanyNameBranch()));
        if (response.getCompanies().isEmpty()) {
            response.setSuccess(false);
            response.setMessage("There is no company like that!");
        } else {
            response.setSuccess(true);
            response.setMessage("Successful!");
        }
        return response;
    }

    @GetMapping("/list/category/{category}")
    public List<Company> listCompaniesByCategory(@PathVariable("category") String category) {
        return compRepo.findCompaniesByRegexpCategory(category);
    }

    @GetMapping("/list/country/{country}")
    public List<Company> listCompaniesByCountry(@PathVariable("country") String country) {
        return compRepo.findCompaniesByRegexpCountry(country);
    }

    @PostMapping("/login")
    public GenericResponse companyLogin(@RequestBody CompanyLoginRequest request) {
        GenericResponse response = new GenericResponse();
        if (compRepo.existsById(request.getCompanyName())) {
            Company company = compRepo.findById(request.getCompanyName()).get();
            if (BCrypt.checkpw(request.getPassword(), company.getCompanyPassword())) {
                response.setSuccess(true);
                response.setMessage("Successful!");
            } else {
                response.setSuccess(false);
                response.setMessage("Wrong password!");
            }
        } else {
            response.setSuccess(false);
            response.setMessage("Wrong company name!");
        }
        return response;
    }

    @PostMapping("/create")
    public CreateCompanyResponse createCompany(@RequestBody CreateCompanyRequest request) {
        CreateCompanyResponse response = new CreateCompanyResponse();
        Company company = new Company();

        if (compRepo.existsById(request.getCompanyNameBranch())) {
            response.setSuccess(false);
            response.setMessage("Company name is already taken!");
        } else {
            if (!ValidationChecks.isValidEmailAddress(request.getCompanyMail())) {
                response.setSuccess(false);
                response.setMessage("Invalid email address!");
            } else {
                CompanyMethods.createCompanyAction(company, compRepo, request, response);
            }

        }
        return response;

    }
}
