package com.edp.business.models;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ForecastingRepo extends ReactiveMongoRepository<Forecasting, String> {
         Flux<Forecasting> getForecastingByCompanyId(String companyId);
         Mono<Forecasting> getForecastingByCompanyIdAndPeriod(String companyId,int period);
}
