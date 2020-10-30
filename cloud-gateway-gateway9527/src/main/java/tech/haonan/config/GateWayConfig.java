package tech.haonan.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yhn
 * @Date: 10/30/2020 8:52 AM
 * @Description:
 **/
@Configuration
public class GateWayConfig {
    //静态路由
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 转发 :9527的请求 /payment/getById/**  到http://localhost:8001/payment/getById
//                .route("path_route1", r -> r.path("/payment/getById/**")
//                        .uri("http://localhost:8001"))
//                // 转发 :9527的请求 /payment/lb/**  到http://localhost:8001/payment/lb/**
//                .route("path_route2", r -> r.path("/payment/lb/**")
//                        .uri("http://localhost:8001")).build();
//    }
    // 动态路由

}


