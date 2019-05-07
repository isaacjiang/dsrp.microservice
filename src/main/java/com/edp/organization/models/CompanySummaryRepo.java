package com.edp.organization.models;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompanySummaryRepo extends ReactiveMongoRepository<CompanySummary, String> {

       Mono<CompanySummary> getCompanyByCompanyIdAndPeriod(String companyId,int period);
}
