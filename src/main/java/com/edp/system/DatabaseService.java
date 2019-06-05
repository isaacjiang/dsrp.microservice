package com.edp.system;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;


@Component
public class DatabaseService {

    @Autowired
    @Qualifier("primaryMongoTemplate")
    MongoTemplate prdb;

    @Autowired
    @Qualifier("operationMongoTemplate")
    MongoTemplate opdb;

    DatabaseService(){}

    public MongoTemplate getPrdb() {
        return prdb;
    }

    public MongoTemplate getOpdb() {
        return opdb;
    }

    public MongoCollection<Document> getPrdbCollection(String collectionName){
        if(!prdb.collectionExists(collectionName))prdb.createCollection(collectionName);
        return prdb.getCollection(collectionName);
    }

    public MongoCollection<Document> getOpdbCollection(String collectionName){
        if(!opdb.collectionExists(collectionName))opdb.createCollection(collectionName);
        return opdb.getCollection(collectionName);
    }
}
