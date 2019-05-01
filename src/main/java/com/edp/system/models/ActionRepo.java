package com.edp.system.models;



import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface ActionRepo extends ReactiveMongoRepository<Action, String> {

}
