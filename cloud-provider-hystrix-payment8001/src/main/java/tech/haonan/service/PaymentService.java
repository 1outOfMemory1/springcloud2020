package tech.haonan.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

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

}