package com.edp.system.models;



import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface ActionRepo extends ReactiveMongoRepository<Action, String> {
    Flux<Action> getActionsByCompanyTypeAndPeriod(String companyType, int period);
}
