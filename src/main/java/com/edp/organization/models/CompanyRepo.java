package com.edp.organization.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepo extends MongoRepository<Company, String> {
    List<Company> findByGroupId(String groupId);

    Company getCompanyById(String companyId);
}
