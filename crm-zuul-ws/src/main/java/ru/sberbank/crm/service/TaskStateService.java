package ru.sberbank.crm.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TaskStateService {

    private final String customersEurekaName = "crm-customers-ws";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EurekaClient eurekaClient;

    public void sendTaskStateToCustomer(Long id, String current) {
        Application application = eurekaClient.getApplication(customersEurekaName);
        InstanceInfo info = application.getInstances().get(0);

        String url = String.format("http://%s:%d/tasks-state?id=%d&current=%s",
                info.getIPAddr(), info.getPort(), id, current);

        restTemplate.postForObject(url, "", String.class);
    }
}
