package tech.haonan.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import tech.haonan.entities.CommonResult;
import tech.haonan.entities.Payment;
import tech.haonan.service.PaymentService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("discovery")
    public Object discovery(){
        List<String>  services= discoveryClient.getServices();
        for(String ele : services){
            log.info("ele  " + ele);
        }
        List<ServiceInstance>   instances= discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance:instances){
            log.info(instance.getServiceId() + "\t" + instance.getHost() + '\t' +instance.getPort() +'\t' +instance.getUri());
        }
        return  discoveryClient;
    }


    @PostMapping(value = "create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*****插入结果： " + result);
        if(result >0)
        {
            return  new CommonResult(200,"插入数据成功，端口号"+serverPort,result);
        }else {
            return new CommonResult(444,"插入数据失败，端口号"+serverPort,null);
        }
    }


    @GetMapping(value = "getById/{id}")
    public CommonResult<Payment> getById(@PathVariable("id") long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("*****查询结果： " + result);
        if(result!=null){
            return new CommonResult(200,"查询成功，端口号"+serverPort,result);
        }else{
            return new CommonResult(444,"没有对应记录,查询的id为"+id +"端口号："+serverPort,null);
        }
    }

    @GetMapping("lb")
    public String getPaymentLB(){
        return serverPort;
    }



    @GetMapping("/feign/timeout")
    public String pymentFeignTimeOut(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
