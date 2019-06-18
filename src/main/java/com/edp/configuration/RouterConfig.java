package com.edp.configuration;

import com.edp.account.AccountWebService;
import com.edp.business.BusinessWebService;
import com.edp.organization.OrganizationDataService;
import com.edp.organization.OrganizationWebService;
import com.edp.system.SystemWebService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> OrgRouterFunction(OrganizationWebService organizationWebService) {
        return nest(path("/api"),
                route(RequestPredicates.GET("/user/all").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getAllSecUsers)
                        // .andRoute(RequestPredicates.POST("/user/login").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::userLogin)
                        .andRoute(RequestPredicates.GET("/user/login/success").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getUserSuccessStatus)
                        .andRoute(RequestPredicates.GET("/user/login/failure").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getUserFailureStatus)
                        .andRoute(RequestPredicates.GET("/user/status/{username}").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getSecUserStatus)
                        .andRoute(RequestPredicates.GET("/group/all").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getAllGroups)
                        .andRoute(RequestPredicates.GET("/company/all").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getAllCompanies)
                        .andRoute(RequestPredicates.GET("/company/base").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getBaseCompanies)
                        .andRoute(RequestPredicates.POST("/user/join").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::userJoinGroup)
                        .andRoute(RequestPredicates.GET("/company/summary/{companyId}").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getCompanySummary)
//                        .andRoute(RequestPredicates.POST("/modify").and(accept(MediaType.APPLICATION_JSON)), organizationDataServiceService::modifyUser)
                        .andRoute(RequestPredicates.POST("/register").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::registerUser)
                        );
    }


    @Bean
    public RouterFunction<ServerResponse> SystemRouterFunction(SystemWebService systemWebService) {
        return nest(path("/api"),
                route(RequestPredicates.GET("/task/{companyId}").and(accept(MediaType.APPLICATION_JSON)), systemWebService::getAction)
                        .andRoute(RequestPredicates.POST("/files/upload").and(accept(MediaType.APPLICATION_JSON)), systemWebService::upload)
//                        .andRoute(RequestPredicates.GET("/user/login/failure").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getUserFailureStatus)
//                        .andRoute(RequestPredicates.GET("/user/status/{username}").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getSecUserStatus)
//                        .andRoute(RequestPredicates.GET("/group/all").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getAllGroups)
//                        .andRoute(RequestPredicates.GET("/company/all").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getAllCompanies)
//                        .andRoute(RequestPredicates.GET("/company/base").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getBaseCompanies)
//                        .andRoute(RequestPredicates.POST("/user/join").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::userJoinGroup)
//                        .andRoute(RequestPredicates.POST("/delete/{username}").and(accept(MediaType.APPLICATION_JSON)), organizationDataServiceService::deleteUser)
//                        .andRoute(RequestPredicates.POST("/modify").and(accept(MediaType.APPLICATION_JSON)), organizationDataServiceService::modifyUser)
//                        .andRoute(RequestPredicates.POST("/register").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::registerUser)
        );
    }
    @Bean
    public RouterFunction<ServerResponse> AccountRouterFunction(AccountWebService accountWebService) {
        return nest(path("/api"),
                route(RequestPredicates.GET("/accountbook/{companyId}").and(accept(MediaType.APPLICATION_JSON)), accountWebService::getAccountBook)
//                        .andRoute(RequestPredicates.POST("/files/upload").and(accept(MediaType.APPLICATION_JSON)), systemWebService::upload)
//                        .andRoute(RequestPredicates.GET("/user/login/failure").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getUserFailureStatus)
//                        .andRoute(RequestPredicates.GET("/user/status/{username}").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getSecUserStatus)
//                        .andRoute(RequestPredicates.GET("/group/all").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getAllGroups)
//                        .andRoute(RequestPredicates.GET("/company/all").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getAllCompanies)
//                        .andRoute(RequestPredicates.GET("/company/base").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getBaseCompanies)
//                        .andRoute(RequestPredicates.POST("/user/join").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::userJoinGroup)
//                        .andRoute(RequestPredicates.POST("/delete/{username}").and(accept(MediaType.APPLICATION_JSON)), organizationDataServiceService::deleteUser)
//                        .andRoute(RequestPredicates.POST("/modify").and(accept(MediaType.APPLICATION_JSON)), organizationDataServiceService::modifyUser)
//                        .andRoute(RequestPredicates.POST("/register").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::registerUser)
        );
    }
    @Bean
    public RouterFunction<ServerResponse> BusinessRouterFunction(BusinessWebService businessWebService) {
        return nest(path("/api"),
                route(RequestPredicates.GET("/forecasting/{companyId}").and(accept(MediaType.APPLICATION_JSON)), businessWebService::getForecasting)
                          .andRoute(RequestPredicates.POST("/forecasting/save").and(accept(MediaType.APPLICATION_JSON)), businessWebService::saveForecasting)
                        .andRoute(RequestPredicates.GET("/employee/{companyId}").and(accept(MediaType.APPLICATION_JSON)), businessWebService::getEmployees)
//                        .andRoute(RequestPredicates.GET("/user/login/success").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getUserSuccessStatus)
//                        .andRoute(RequestPredicates.GET("/user/login/failure").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getUserFailureStatus)
//                        .andRoute(RequestPredicates.GET("/user/status/{username}").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getSecUserStatus)
//                        .andRoute(RequestPredicates.GET("/group/all").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getAllGroups)
//                        .andRoute(RequestPredicates.GET("/company/all").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getAllCompanies)
//                        .andRoute(RequestPredicates.GET("/company/base").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::getBaseCompanies)
//                        .andRoute(RequestPredicates.POST("/user/join").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::userJoinGroup)
//                        .andRoute(RequestPredicates.POST("/delete/{username}").and(accept(MediaType.APPLICATION_JSON)), organizationDataServiceService::deleteUser)
//                        .andRoute(RequestPredicates.POST("/modify").and(accept(MediaType.APPLICATION_JSON)), organizationDataServiceService::modifyUser)
//                        .andRoute(RequestPredicates.POST("/register").and(accept(MediaType.APPLICATION_JSON)), organizationWebService::registerUser)
        );
    }

}

