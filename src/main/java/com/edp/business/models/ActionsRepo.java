package com.edp.business.models;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ActionsRepo extends ReactiveMongoRepository<Actions, String> {

}
