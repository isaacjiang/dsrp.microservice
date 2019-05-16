package com.edp.system;


import com.edp.organization.OrganizationDataService;
import com.edp.organization.models.Company;
import com.edp.organization.models.Group;
import com.edp.organization.models.SecUser;
import com.edp.system.models.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class SystemWebService {

    @Autowired
    private SystemDataService systemDataService;
    @Autowired
    private OrganizationDataService organizationDataService;

    Mono<ServerResponse> userNotFound = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(new SecUser()), SecUser.class);

    public SystemWebService() {}


    /**
     * GET ALL Users info from database
     */

    public Mono<ServerResponse> getAction(ServerRequest request) {
        String companyId = request.pathVariable("companyId");
        Company company = organizationDataService.getCompany(companyId);
        List<Action> actionsList = systemDataService.getActionByCompany(company.getCompanyType(), company.getInPeriod());
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.fromIterable(actionsList), Action.class);
    }






}
