package com.edp.system.models;



import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface PeriodRepo extends ReactiveMongoRepository<Period, String> {
    Mono<Period> getPeriodByPeriod(int period);
    Mono<Period> getPeriodByStatus(String status);

}
