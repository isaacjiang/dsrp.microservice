package com.edp.organization.models;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanySummaryRepo extends MongoRepository<CompanySummary, String> {

       CompanySummary getCompanyByCompanyIdAndPeriod(String companyId,int period);
}
