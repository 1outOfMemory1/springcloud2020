package tech.haonan.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yhn
 * @Date: 11/5/2020 7:53 PM
 * @Description:
 **/
@RestController
@RefreshScope  // 这个注解是支持动态刷新读取3344的配置 这样就可以做到 修改github不重启微服务
public class HelloHandler {
    @Value("${config.info}")  // 通过3344 端口的 config server 来拿到信息 然后把它自动注入到 configInfo变量中去
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}
