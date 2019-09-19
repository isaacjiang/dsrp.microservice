package com.edp.organization;


import com.edp.organization.models.SecUser;
import com.edp.organization.models.SecUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;


@Service
public class SecUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    SecUserRepo secUserRepo;


    public SecUserDetailsService() {

    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
//        System.out.println(username);
        SecUser secUser = secUserRepo.getSecUserByUsername(username);
        return Mono.just(new User(secUser.getUsername(), secUser.getPassword(), Collections.singleton(new SimpleGrantedAuthority(secUser.getAuthorities()))));
    }


}
