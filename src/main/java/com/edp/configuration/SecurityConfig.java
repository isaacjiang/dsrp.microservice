package com.edp.configuration;


import com.edp.organization.OrganizationDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;


@Configuration
public class SecurityConfig {

    @Bean
    public ReactiveAuthenticationManager authenticationManager(OrganizationDataService organizationDataServiceService) {
        // init to defualt state
        organizationDataServiceService.initAdminUser(); // hard coded admin user
        return new UserDetailsRepositoryReactiveAuthenticationManager(organizationDataServiceService);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //System.out.println("Web chain handling");
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/api/login").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/register").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
                .anyExchange()
                .authenticated()
                .and()
                .requestCache()
                .and()
                .formLogin()
                .loginPage("/api/login")
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/api/user/login/success"))
                .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/api/user/login/failure"));

        return http.build();
    }

}

