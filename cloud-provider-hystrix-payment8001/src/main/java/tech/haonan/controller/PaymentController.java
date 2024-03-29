package tech.haonan.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.haonan.service.PaymentService;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;


    /**
     *
     * @param id  id是用来凑数的没用
     * @return 返回一个字符串
     */
    @GetMapping("payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_OK(id);
        log.info("****** result : " +result);
        return result;
    }
    @GetMapping("payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("****** result : " +result);
        return result;
    }

    // 上边是服务降级
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    //========= 熔断


    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("*****result:" + result);
        return result;
    }



}
