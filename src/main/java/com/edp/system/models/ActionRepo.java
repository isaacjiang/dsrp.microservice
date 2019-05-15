package com.edp.system.models;



import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ActionRepo extends MongoRepository<Action, String> {
    Action getActionById(String id);
    List<Action> getActionsByCompanyTypeAndPeriod(String companyType, int period);
}
