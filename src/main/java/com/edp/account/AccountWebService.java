package com.edp.account;


import com.edp.account.models.AccountBook;
import com.edp.organization.OrganizationDataService;
import com.edp.organization.models.Company;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AccountWebService {

    @Autowired
    private AccountDataService accountDataService;
    @Autowired
    private OrganizationDataService organizationDataService;


    public AccountWebService() {
    }


    /**
     * GET ALL Users info from database
     */

    public Mono<ServerResponse> getAccountBook(ServerRequest request) {
        String companyId = request.pathVariable("companyId");
        String type = request.pathVariable("type");
//        Company company = organizationDataService.getCompany(companyId);

        JSONObject accountBookList = accountDataService.getAccountBookCom(companyId,type);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(accountBookList.toString()), String.class);
    }

}
