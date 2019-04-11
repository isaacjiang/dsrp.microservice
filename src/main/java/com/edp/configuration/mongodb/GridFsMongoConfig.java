package com.edp.configuration.mongodb;


import com.mongodb.MongoClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;


@Configuration //Configuration class
@EnableConfigurationProperties(GridFsMongoConfig.class)
@ConfigurationProperties(prefix = "gridfs.mongodb") //Defines my custom prefix and points to the primary db properties
public class GridFsMongoConfig extends AbstractMongoConfiguration {

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    @Override
    public MongoClient mongoClient() {
        return new MongoClient(host,port);
    }

    /**
     * Method that creates MongoDbFactory
     * Common to both of the MongoDb connections
     */
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(mongoClient(), database);
    }

    @Bean(name = "gridFsTemplate")
    public GridFsTemplate getMongoTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(),mappingMongoConverter());
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }


}
