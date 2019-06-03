package com.edp.account.models;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface AccTitleRepo extends MongoRepository<AccTitle, String> {
    AccTitle getAccTitleById(String id);

}
