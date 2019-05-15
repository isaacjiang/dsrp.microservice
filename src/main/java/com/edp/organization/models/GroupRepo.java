package com.edp.organization.models;


import org.springframework.data.mongodb.repository.MongoRepository;


public interface GroupRepo extends MongoRepository<Group, String> {
         Group getGroupById(String id);

}
