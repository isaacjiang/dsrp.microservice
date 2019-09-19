package com.edp.system.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepo extends MongoRepository<Period, String> {
    Period getPeriodByPeriod(int period);

    Period getPeriodByStatus(String status);

}
