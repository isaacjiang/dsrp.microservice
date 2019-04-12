package com.edp.configuration.mongodb;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@Configuration //Configuration class
@EnableConfigurationProperties(OperationMongoConfig.class)
@ConfigurationProperties(prefix = "operation.mongodb") //Defines my custom prefix and points to the primary db properties
@EnableReactiveMongoRepositories(basePackages ={"com.edp"},reactiveMongoTemplateRef = "operationMongoTemplate")
public class OperationMongoConfig extends AbstractMongoConfig {


    @Override
    @Bean(name="operationMongoTemplate")
    public ReactiveMongoTemplate getMongoTemplate() throws Exception {
        return new ReactiveMongoTemplate(mongoDbFactory());
    }


}
