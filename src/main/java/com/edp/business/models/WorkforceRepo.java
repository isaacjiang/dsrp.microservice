package com.edp.business.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkforceRepo extends MongoRepository<Workforce, String> {

}
