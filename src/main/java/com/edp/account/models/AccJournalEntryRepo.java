package com.edp.account.models;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface AccJournalEntryRepo extends MongoRepository<AccJournalEntry, String> {

    AccJournalEntry getAccJournalEntryByAccountBookAndTitleIdAndReference(AccountBook accountBook,String titleId,String reference);

}
