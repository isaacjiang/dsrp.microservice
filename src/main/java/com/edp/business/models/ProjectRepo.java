package com.edp.business.models;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProjectRepo extends ReactiveMongoRepository<Project, String> {

}
