package com.ok.dao;

import com.ok.entities.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface CompanyRepository extends MongoRepository<Company, String> {


    @Query(value = "{'companyNameBranch': {$regex : '^?0$', $options: 'i'}}")
    Company findByCompanyNameIgnoreCase(String companyNameBranch);

    @Query("{ 'companyNameBranch' : { $regex: '?0', $options: 'i'} }")
    List<Company> findCompaniesByRegexpName(String companyNameBranch);

    @Query("{ 'category' : { $regex: '?0', $options: 'i'} }")
    List<Company> findCompaniesByRegexpCategory(String category);

    @Query("{ 'country' : { $regex: '?0', $options: 'i'} }")
    List<Company> findCompaniesByRegexpCountry(String country);

}
