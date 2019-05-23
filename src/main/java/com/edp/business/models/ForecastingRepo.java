package com.edp.business.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForecastingRepo extends MongoRepository<Forecasting, String> {
         List<Forecasting> getForecastingByCompanyId(String companyId);
         Forecasting getForecastingByCompanyIdAndPeriod(String companyId,int period);
}
