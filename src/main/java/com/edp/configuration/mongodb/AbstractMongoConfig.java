package com.edp.configuration.mongodb;


import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;


public abstract class AbstractMongoConfig extends AbstractReactiveMongoConfiguration {
    //Mongo DB Properties
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



    /**
     * Method that creates MongoDbFactory
     * Common to both of the MongoDb connections
     */
    public ReactiveMongoDatabaseFactory mongoDbFactory() throws Exception {
        return new SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(), database);
    }


    public MongoClient reactiveMongoClient() {
        return MongoClients.create( new ConnectionString("mongodb://"+host+":"+port));
    }


    protected String getDatabaseName() {
        return this.database;
    }
    /**
     * Factory method to create the MongoTemplate
     */
    abstract public ReactiveMongoTemplate getMongoTemplate() throws Exception;
}
