package com.edp.account;


import com.edp.business.BusinessDataService;
import com.edp.organization.OrganizationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountWebService {

    @Autowired
    private BusinessDataService businessDataService;
    @Autowired
    private OrganizationDataService organizationDataService;


    public AccountWebService() {
    }


    /**
     * GET ALL Users info from database
     */

//    public Mono<ServerResponse> getForecasting(ServerRequest request) {
//        String companyId = request.pathVariable("companyId");
////        int period = Integer.parseInt(request.pathVariable("period"));
//        Company company = organizationDataService.getCompany(companyId);
//
//        Forecasting forecasting =  businessDataService.getForecastingByCompanyIdAndPeriod(company.getId(), company.getInPeriod());
//        if (forecasting==null){
//            forecasting = new Forecasting().setCompanyId(companyId);
//        }
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(forecasting), Forecasting.class);
//    }

}
