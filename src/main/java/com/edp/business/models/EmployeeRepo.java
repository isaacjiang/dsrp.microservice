package com.edp.business.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, String> {
    List<Employee> getEmployeesByCompanyTypeAndPeriod(String companyType, int period);

}
