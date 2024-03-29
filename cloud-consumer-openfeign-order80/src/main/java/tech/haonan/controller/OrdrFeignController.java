package tech.haonan.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.haonan.entities.CommonResult;
import tech.haonan.entities.Payment;
import tech.haonan.service.PaymentFeignService;

@RestController
@Slf4j
public class OrdrFeignController {
    @Autowired
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/getById/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/consumer/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        //openfeign-ribbon 客户端一般默认等待1秒钟
        return  paymentFeignService.paymentFeignTimeOut();
    }

}
