package com.edp.system.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends MongoRepository<Task, String> {
    Task getActionById(String id);

    List<Task> getActionsByCompanyTypeAndPeriod(String companyType, int period);
}
