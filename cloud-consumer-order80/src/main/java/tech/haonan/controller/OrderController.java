package tech.haonan.controller;

import com.netflix.loadbalancer.ILoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tech.haonan.entities.CommonResult;
import tech.haonan.entities.Payment;
import tech.haonan.lb.MyLoadBanlancer;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("consumer")
public class OrderController {
    //public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    private MyLoadBanlancer MyLoadBanlancer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,CommonResult.class);
    }

    @GetMapping("payment/getById/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") long id){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getById/"+id,CommonResult.class);
    }

    @GetMapping("payment/getEntity/{id}")
    public CommonResult<Payment> getEntity(@PathVariable("id") long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getById/"+id,CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else return new CommonResult<>(444,"操作失败");
    }


    @GetMapping("payment/MYLB")
    public String MYLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() <=0)
            return null;

        ServiceInstance serviceInstance = MyLoadBanlancer.instance(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri+"payment/lb",String.class);
    }
}
