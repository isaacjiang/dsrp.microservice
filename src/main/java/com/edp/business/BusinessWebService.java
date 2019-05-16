package com.edp.business;


import com.edp.business.models.Forecasting;
import com.edp.organization.OrganizationDataService;
import com.edp.organization.models.Company;
import com.edp.organization.models.SecUser;
import com.edp.system.SystemDataService;
import com.edp.system.models.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;


@Service
public class BusinessWebService {

    @Autowired
    private BusinessDataService businessDataService;
    @Autowired
    private OrganizationDataService organizationDataService;


    public BusinessWebService() {
    }


    /**
     * GET ALL Users info from database
     */

    public Mono<ServerResponse> getForecasting(ServerRequest request) {
        String companyId = request.pathVariable("companyId");
//        int period = Integer.parseInt(request.pathVariable("period"));
        Company company = organizationDataService.getCompany(companyId);

        Forecasting forecasting =  businessDataService.getForecastingByCompanyIdAndPeriod(company.getId(), company.getInPeriod());
        if (forecasting==null){
            forecasting = new Forecasting().setCompanyId(companyId);
        }
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(forecasting), Forecasting.class);
    }

    public Mono<ServerResponse> saveForecasting(ServerRequest request) {
        Mono<Forecasting> forecastingMono = request.bodyToMono(Forecasting.class)
                .flatMap(forecasting -> {
                    Company company = organizationDataService.getCompany(forecasting.getCompanyId());
                    Forecasting forecasting1 = forecasting.setPeriod(company.getInPeriod());
                    businessDataService.saveForecasting(forecasting1);
                    return  Mono.just(forecasting1);
                }
               ).cache();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(forecastingMono, Forecasting.class);
    }
}
