package com.edp.account.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface IndexEntryRepo extends MongoRepository<IndexEntry, String> {

}
