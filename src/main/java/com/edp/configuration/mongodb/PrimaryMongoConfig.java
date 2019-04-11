package com.edp.configuration.mongodb;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@Configuration //Configuration class
@EnableConfigurationProperties(PrimaryMongoConfig.class)
@ConfigurationProperties(prefix = "primary.mongodb") //Defines my custom prefix and points to the primary db properties
@EnableReactiveMongoRepositories(basePackages ={"com.edp.system"})
public class PrimaryMongoConfig extends AbstractMongoConfig {

    @Primary
    @Override
    @Bean(name = "reactiveMongoTemplate")
    public ReactiveMongoTemplate getMongoTemplate() throws Exception {
        return new ReactiveMongoTemplate(mongoDbFactory());
    }
}
