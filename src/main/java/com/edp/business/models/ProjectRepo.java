package com.edp.business.models;


import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProjectRepo extends MongoRepository<Project, String> {

}
