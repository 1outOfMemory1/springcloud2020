package tech.haonan.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "线程池 " + Thread.currentThread().getName() + "PaymentInfo_OK,id =" + id + "访问成功了";
    }

    //这里注解主要是设置等待时间是3秒 如果超出3秒 那么8001服务的 paymentInfo_TimeOut 直接会调用兜底函数  这里故意睡眠了5秒 所以一定会调用兜底函数
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds" , value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id){
//        int timeNum = 5; ////////////// 注意这里已经修改成 5秒了
//        try {  //设置睡眠3秒钟 肯定超时
//            TimeUnit.SECONDS.sleep(timeNum);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        int a = 10/0;
        return "线程池 " + Thread.currentThread().getName() + "paymentInfo_TimeOut,id =" + id + "访问超时" ;
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return  "我是兜底函数";
    }


    // 上边是服务降级
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    //========= 熔断

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            //com.netflix.hystrix.HystrixCommandProperties  所有的properties定义在这里边
         @HystrixProperty(name = "circuitBreaker.enabled",value = "true") ,// 是否开启断路器
         @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"), // 请求次数
         @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000") ,// 时间窗口期 进过多少时间恢复一次尝试
         @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60") // 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id<0){
            throw new RuntimeException("!!!!!!!!! id 不能为负数");
        }
        // 糊涂工具包用来生成随机数 流水号
        String serialNumber = IdUtil.simpleUUID();
        // 等价于  UUID.randomUUID().toString(); 糊涂工具包 很好用 用来加密解密 等一些操作
        return Thread.currentThread().getName() + "调用成功 流水号为： " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数,请重试  此次输入的 id 为" + id;
    }




}