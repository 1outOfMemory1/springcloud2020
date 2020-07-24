package tech.haonan.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface MyLoadBanlancer {
    ServiceInstance instance(List<ServiceInstance> serviceInstances);
}
