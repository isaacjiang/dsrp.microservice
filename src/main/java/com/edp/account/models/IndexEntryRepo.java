package com.edp.account.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface IndexEntryRepo extends MongoRepository<IndexEntry, String> {
   IndexEntry getIndexEntryByAccountBookAndIndexNameAndReferenceAndTitleId(AccountBook accountBook,String indexName,String reference,String titleId);
   List<IndexEntry> getIndexEntriesByAccountBookAndIndexName(AccountBook accountBook,String indexName);
    List<IndexEntry> getIndexEntriesByAccountBookAndIndexNameAndTitleId(AccountBook accountBook,String indexName,String titleId);
}
