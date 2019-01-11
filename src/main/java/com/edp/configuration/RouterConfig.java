package com.edp.configuration;

import com.edp.system.SecUserDetails;
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
    public RouterFunction<ServerResponse> userRouterFunction(SecUserDetails secUserDetailsService) {
        return nest(path("/api/user"),
                route(RequestPredicates.GET("/all").and(accept(MediaType.APPLICATION_JSON)), secUserDetailsService::getAll)
                        .andRoute(RequestPredicates.GET("/login/success").and(accept(MediaType.APPLICATION_JSON)), secUserDetailsService::getUserSuccessStatus)
                        .andRoute(RequestPredicates.GET("/login/failure").and(accept(MediaType.APPLICATION_JSON)), secUserDetailsService::getUserFailureStatus)
                        .andRoute(RequestPredicates.GET("/{username}").and(accept(MediaType.APPLICATION_JSON)), secUserDetailsService::getUserStatus)
                        .andRoute(RequestPredicates.POST("/logout").and(accept(MediaType.APPLICATION_JSON)), secUserDetailsService::UserLogout)
                        .andRoute(RequestPredicates.POST("/checkexist/{username}").and(accept(MediaType.APPLICATION_JSON)), secUserDetailsService::checkUserStatus)
                        .andRoute(RequestPredicates.POST("/delete/{username}").and(accept(MediaType.APPLICATION_JSON)), secUserDetailsService::deleteUser)
                        .andRoute(RequestPredicates.POST("/modify").and(accept(MediaType.APPLICATION_JSON)), secUserDetailsService::modifyUser)
                        .andRoute(RequestPredicates.POST("/register").and(accept(MediaType.APPLICATION_JSON)), secUserDetailsService::registerUser));
    }

//
//    @Bean
//    public RouterFunction<ServerResponse> queryTimeSeriesWebService(TimeSeriesWebService timeSeriesWebService) {
//        return nest(path("/api/ts"),
//                route(RequestPredicates.GET("/{channel}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryTopSwitchInfo)
////                        .andRoute(RequestPredicates.GET("/mcs/{ip}/{n}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryMulticastDest)
////                        .andRoute(RequestPredicates.GET("/if/{ip}/{n}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryInterfaceOverview)
////                        .andRoute(RequestPredicates.GET("/switchinfo").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryTopSwitchInfo)
//                        .andRoute(RequestPredicates.POST("/iflist").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryInterfaceOverviewByList)
////                        .andRoute(RequestPredicates.POST("/mclist").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryMulticastByList)
////                        .andRoute(RequestPredicates.GET("/ifhis/{ip}/{frame}/{start}/{end}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryInterfaceOverviewHis)
////                        .andRoute(RequestPredicates.GET("/mchis/{ip}/{mip}/{timestamp}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryMulticastHis)
//        );
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> SwitchService(SwitchService switchService) {
//        return nest(path("/api/switch"),
//                route(RequestPredicates.POST("/setswitch").and(accept(MediaType.APPLICATION_JSON)), switchService::SetSwitch)
//                        .andRoute(RequestPredicates.GET("/getswitch").and(accept(MediaType.APPLICATION_JSON)), switchService::GetSwitch)
////                        .andRoute(RequestPredicates.GET("/mchis/{ip}/{mip}/{timestamp}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryMulticastHis)
//        );
//    }


//    @Bean
//    public RouterFunction<ServerResponse> searchRouterFunction(SearchService searchService) {
//        return nest(path("/api/search"),
//                route(RequestPredicates.POST("").and(accept(MediaType.APPLICATION_JSON)), searchService::searching)
//                        .andRoute(RequestPredicates.POST("/mc").and(accept(MediaType.APPLICATION_JSON)), searchService::searchMulticastRoute)
//        );
//    }

//    @Bean
//    public RouterFunction<ServerResponse> switchDataRouterFunction(TimeSeriesWebService timeSeriesWebService) {
//        return nest(path("/api/sd"),
//                route(RequestPredicates.GET("/mcd/{ip}/{n}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryMulticastDest)
//                    .andRoute(RequestPredicates.GET("/mcs/{ip}/{n}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryMulticastDest)
//                    .andRoute(RequestPredicates.GET("/if/{ip}/{n}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryInterfaceOverview)
//                    .andRoute(RequestPredicates.GET("/env/{ip}/{n}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryTopSwitchInfo)
//                    .andRoute(RequestPredicates.POST("/iflist").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryInterfaceOverviewByList)
//                    .andRoute(RequestPredicates.POST("/mclist").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryMulticastByList)
//                    .andRoute(RequestPredicates.GET("/ifhis/{ip}/{frame}/{start}/{end}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryInterfaceOverviewHis)
//                    .andRoute(RequestPredicates.GET("/mchis/{ip}/{mip}/{timestamp}").and(accept(MediaType.APPLICATION_JSON)), timeSeriesWebService::queryMulticastHis)
//        );
//    }


//    @Bean
//    public RouterFunction<ServerResponse> systemRouterFunction(SystemDataService systemDataService) {
//        return nest(path("/api"),
//                route(RequestPredicates.GET("/sys/lock").and(accept(MediaType.APPLICATION_JSON)), systemDataService::getDataLockStatus)
//                        .andRoute(RequestPredicates.POST("/sys/lock").and(accept(MediaType.APPLICATION_JSON)), systemDataService::setDataLockStatus));
//    }

}

