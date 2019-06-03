package com.edp.system;

import com.edp.interfaces.MicroServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;


@Component
public class DatabaseService implements MicroServiceInterface {

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

    @Override
    public MicroServiceInterface start() {
        Thread thr = new Thread(this, this.getClass().getName());
        thr.setName("Service@Database");
        thr.start();
        return this;
    }

    @Override
    public void schedule() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {

    }
}
