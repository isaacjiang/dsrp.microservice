package com.edp.account;


import com.edp.account.models.AccJournalEntry;
import com.edp.account.models.AccJournalEntryRepo;
import com.edp.account.models.AccTitleRepo;
import com.edp.interfaces.MicroServiceInterface;
import com.edp.organization.OrganizationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountDataService implements MicroServiceInterface {

    @Autowired
    private AccTitleRepo accTitleRepo;

    @Autowired
    private AccJournalEntryRepo accJournalEntryRepo;

    @Autowired
    private OrganizationDataService organizationDataService;

    public AccountDataService() {

    }


    @Override
    public MicroServiceInterface start() {
        Thread thr = new Thread(this, this.getClass().getName());
        thr.setName("Service@Account");
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
        this.initialization();
    }


    /**
     * the method hard coded admin user into database with role Admin
     */


    public void initialization() {


    }

    public void bookkeeping(AccJournalEntry accJournalEntry) {
//        if (accJournalEntryRepo.)
        accJournalEntryRepo.save(accJournalEntry);

    }

    public void bookkeeping(String companyId, int period, String titleId, String reference, double value, String memo) {
        AccJournalEntry accJournalEntry = accJournalEntryRepo.getAccJournalEntriesByCompanyIdAndPeriodAndTitleIdAndReference(companyId, period, titleId, reference);

        if (accJournalEntry == null) {
            accJournalEntry = new AccJournalEntry();
        }
        accJournalEntry.setCompanyId(companyId).setPeriod(period).setTitleId(titleId).setTitle(accTitleRepo.getAccTitleById(titleId).getTitle()).setReference(reference).setValue(value).setMemo(memo);

        accJournalEntryRepo.save(accJournalEntry);

    }


}
