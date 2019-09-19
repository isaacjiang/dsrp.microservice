package com.edp.organization.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecUserRepo extends MongoRepository<SecUser, String> {
    SecUser getSecUserByUsername(String username);

    List<SecUser> findAll();


}
