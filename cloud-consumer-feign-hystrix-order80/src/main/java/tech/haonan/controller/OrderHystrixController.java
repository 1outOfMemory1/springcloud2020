package tech.haonan.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.haonan.service.PaymentHystrixService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yhn
 * @Date: 2020/9/9 21:21
 * @Description:
 **/
@RestController
@Slf4j
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;
    @GetMapping("consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }
//    @GetMapping("consumer/payment/hystrix/timeout/{id}")
//    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
//        String result = paymentHystrixService.paymentInfo_TimeOut(id);
//        return result;
//    }

    //这里注解主要是设置等待时间是3秒 如果超出3秒 那么8001服务的 paymentInfo_TimeOut 直接会调用兜底函数  这里故意睡眠了5秒 所以一定会调用兜底函数
    @GetMapping("consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds" , value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id){
        int timeNum = 5; ////////////// 注意这里已经修改成 5秒了
        try {  //设置睡眠3秒钟 肯定超时
            TimeUnit.SECONDS.sleep(timeNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        int a = 10/0;
        return "线程池 " + Thread.currentThread().getName() + "paymentInfo_TimeOut,id =" + id + "访问超时" ;
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return  "我是消费者端(80)的兜底函数";
    }
}
