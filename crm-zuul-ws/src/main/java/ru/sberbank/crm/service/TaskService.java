package ru.sberbank.crm.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.crm.entity.Task;

@Service
public class TaskService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EurekaClient eurekaClient;

    public void sendTaskToRecipient(Task task, String recipient) {
        Application application = eurekaClient.getApplication(recipient);
        InstanceInfo info = application.getInstances().get(0);

        String url = String.format("http://%s:%d/send",
                info.getIPAddr(), info.getPort());

        restTemplate.postForObject(url, task, String.class);
    }
}
