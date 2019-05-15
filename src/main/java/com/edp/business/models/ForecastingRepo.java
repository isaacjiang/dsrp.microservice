package com.edp.business.models;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ForecastingRepo extends MongoRepository<Forecasting, String> {
         List<Forecasting> getForecastingByCompanyId(String companyId);
         Forecasting getForecastingByCompanyIdAndPeriod(String companyId,int period);
}
