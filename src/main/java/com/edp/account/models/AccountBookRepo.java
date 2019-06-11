package com.edp.account.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface AccountBookRepo extends MongoRepository<AccountBook, String> {
    AccountBook getAccountBookById(String id);
    AccountBook getAccountBookByCompanyIdAndPeriod(String companyId,int period);


}
