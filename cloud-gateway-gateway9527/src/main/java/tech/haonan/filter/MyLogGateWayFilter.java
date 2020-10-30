package tech.haonan.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @Author: yhn
 * @Date: 10/30/2020 7:57 PM
 * @Description:
 **/
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    // 过滤  如果里边没有uname这个属性 那么返回错误  并打印到日志中去
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("********** come in MyLogGateWayFilter: " + new Date());
        String uname =  exchange.getRequest().getQueryParams().getFirst("uname");
        if(uname == null){
            log.info("********** 姓名为null，非法用户");
            //  如果没有uname这个属性 直接返回状态 406(NOT_ACCEPTABLE)
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        // 去下一个过滤链进行 过滤
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        // 加载过滤器的顺序 数字越小 优先级越高
        return 0;
    }
}
