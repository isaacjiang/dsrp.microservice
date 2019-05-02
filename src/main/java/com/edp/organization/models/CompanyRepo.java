package com.edp.organization.models;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompanyRepo extends ReactiveMongoRepository<Company, String> {
       Flux<Company> findByGroupId(String groupId);
       Mono<Company> getCompanyById(String companyId);
}
