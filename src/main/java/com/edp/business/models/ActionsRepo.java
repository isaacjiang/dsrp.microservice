package com.edp.business.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionsRepo extends MongoRepository<Actions, String> {

}
