package tech.haonan.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feinLoggerLever(){
        return Logger.Level.FULL;
    }
}
