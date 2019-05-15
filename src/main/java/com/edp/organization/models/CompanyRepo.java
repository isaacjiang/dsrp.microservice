package com.edp.organization.models;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CompanyRepo extends MongoRepository<Company, String> {
       List<Company> findByGroupId(String groupId);
       Company getCompanyById(String companyId);
}
