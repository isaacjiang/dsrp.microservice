package com.edp.system;


import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.edp.interfaces.MicroServiceInterface;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Service
public class MongoDbService implements MicroServiceInterface {

    private MongoClient client;
    private MongoClient clientMNM;
    private MongoDatabase dbn;
    private MongoTemplate mongoTemplate;

    @Autowired
    private ParametersService parametersService;

    public MongoDbService() {}

    @Override
    public MongoDbService start() {
        Thread thr = new Thread(this, this.getClass().getName());
        thr.setName("Service@MongoDB");
        thr.start();
        return this;
    }

    @Override
    public void run() {
        String host = this.parametersService.getParameters("spring.data.mongodb.host");
        int port = Integer.valueOf(this.parametersService.getParameters("spring.data.mongodb.port"));
        client =new MongoClient(host,port);
        clientMNM=new MongoClient(parametersService.getParameters("nvrt.application.mongodb.ip"), Integer.valueOf(parametersService.getParameters("nvrt.application.mongodb.port")));
        dbn = client.getDatabase(this.parametersService.getParameters("spring.data.mongodb.database"));
        System.out.println("MongoDB connected to " + host);
    }

    public MongoClient getClient(){
        return client;
    }
    public MongoClient getClientMNM(){
        return clientMNM;
    }

    public MongoDatabase getDatabase(String databaseName) {
        return client.getDatabase(databaseName);
    }

    public MongoDatabase getDefaultDb() {
        return dbn;
    }

    public MongoCollection<Document> getCollection(String collectionName){
        return getDefaultDb().getCollection(collectionName);
    }
    public void insertOne(String collectionName,JSONObject jsonObject) {
        getDefaultDb().getCollection(collectionName).insertOne(Document.parse(jsonObject.toString()));
    }
    public void updateOne(String collectionName,String id,JSONObject jsonObject) {
        getDefaultDb().getCollection(collectionName).replaceOne(eq("_id",id),Document.parse(jsonObject.toString()), new UpdateOptions().upsert( true ));
    }

    @Override
    public void schedule() {

    }

    @Override
    public void stop() {
      this.client.close();
    }


    public MongoTemplate getMongoTemplate() {
        mongoTemplate = new MongoTemplate(client,"dbn");
        return mongoTemplate;
    }

    public JSONArray getDbmnmCollection(String db, String collection, BasicDBObject deviceConditions) {
        List<JSONObject> device =  clientMNM.getDatabase(db).getCollection(collection)
                .find(deviceConditions).map(document -> new JSONObject(document.toJson()))
                .into(new ArrayList<>());
        JSONArray results = new JSONArray();
        for(int i=0;i<device.size();i++){
            results.put(device.get(i));
        }
        return results;
    }

    public void updateOne(String db,String collectionName,String id,JSONObject jsonObject) {
        client.getDatabase(db).getCollection(collectionName).updateOne(eq("_id", new ObjectId(id)), new Document("$set",Document.parse(jsonObject.toString())), new UpdateOptions().upsert(true));
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}