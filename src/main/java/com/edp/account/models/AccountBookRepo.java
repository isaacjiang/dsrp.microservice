package com.edp.account.models;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface AccountBookRepo extends MongoRepository<AccountBook, String> {
    AccountBook getAccountBookById(String id);
    AccountBook getAccountBookByCAndCompanyIdAndPeriod(String companyId,int period);

}
