package com.edp.configuration;


import com.mongodb.MongoClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration //Configuration class
@EnableConfigurationProperties(GridFsMongoConfig.class)
@ConfigurationProperties(prefix = "gridfs.mongodb") //Defines my custom prefix and points to the primary db properties
@EnableMongoRepositories(basePackages ={"com.edp.fileservice"},mongoTemplateRef = "gridFsTemplate")
public class GridFsMongoConfig {

    private String host;
    private int port;
    private String database;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabaseName(){
        return this.database;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public MongoClient mongoClient() {
        return new MongoClient(this.host,this.port);
    }

    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(this.mongoClient(), this.getDatabaseName());
    }

    @Bean
    public GridFsTemplate getGridFsTemplate(){
        return new GridFsTemplate(mongoDbFactory(),new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()), new MongoMappingContext()));
    }
}
