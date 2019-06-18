package com.edp.account.models;

import com.edp.account.AccountWebService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface AccJournalEntryRepo extends MongoRepository<AccJournalEntry, String> {

    AccJournalEntry getAccJournalEntryByAccountBookAndTitleIdAndReference(AccountBook accountBook,String titleId,String reference);
    List<AccJournalEntry> getAccJournalEntrysByAccountBookAndTitleId(AccountBook accountBook, String titleId);
    List<AccJournalEntry> getAccJournalEntriesByAccountBook(AccountBook accountBook);
}
