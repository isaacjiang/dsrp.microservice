package com.edp.models.system;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SecUserRepo extends ReactiveMongoRepository<SecUser, String> {
    Mono<UserDetails> getUserDetailsByUsername(String username);

    Mono<SecUser> getSecUserByUsername(String username);
    Flux<SecUser> findAll();

}
