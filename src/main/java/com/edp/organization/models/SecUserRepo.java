package com.edp.organization.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface SecUserRepo extends MongoRepository<SecUser, String> {
    UserDetails getUserDetailsByUsername(String username);
    SecUser getSecUserByUsername(String username);
    List<SecUser> findAll();


}
