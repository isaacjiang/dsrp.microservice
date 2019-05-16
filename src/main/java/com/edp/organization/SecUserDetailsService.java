package com.edp.organization;



import com.edp.organization.models.SecUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
public class SecUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    SecUserRepo secUserRepo;


    public SecUserDetailsService() {

    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        System.out.println(username);
        return Mono.just(secUserRepo.getUserDetailsByUsername(username));
    }




}
