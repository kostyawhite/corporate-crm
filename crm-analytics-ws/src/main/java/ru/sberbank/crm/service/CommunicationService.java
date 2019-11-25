package ru.sberbank.crm.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.crm.entity.Department;
import ru.sberbank.crm.entity.Task;
import ru.sberbank.crm.wrapper.DepartmentList;

import java.util.Collections;
import java.util.List;

@Service
public class CommunicationService {

    private final String routerEurekaName = "crm-zuul-ws";
    private final String serviceTitle = "Отдел аналитиков";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    public List<Department> getDepartmentsFromRouter(Long selfDepartmentId, Long taskTemplateId) {

        Application application = eurekaClient.getApplication(routerEurekaName);
        InstanceInfo info = application.getInstances().get(0);

        String url = String.format("http://%s:%d/departments?id=%s&templateId=%s",
                info.getIPAddr(), info.getPort(), selfDepartmentId, taskTemplateId);
        DepartmentList response = restTemplate.getForObject(url, DepartmentList.class);

        if (response != null) {
            return response.getDepartmentsList();
        } else {
            return Collections.emptyList();
        }
    }

    public void sendTaskToDepartmentViaRouter(Task task, String recipientDepartmentName) {

        Application application = eurekaClient.getApplication(routerEurekaName);
        InstanceInfo info = application.getInstances().get(0);

        String url = String.format("http://%s:%d/tasks?recipient=%s",
                info.getIPAddr(), info.getPort(), recipientDepartmentName);

        restTemplate.postForObject(url, task, String.class);
    }

    public void sendTaskStatusChange(Long id) {
        Application application = eurekaClient.getApplication(routerEurekaName);
        InstanceInfo info = application.getInstances().get(0);

        String url = String.format("http://%s:%d/tasks-state?id=%d&current=%s",
                info.getIPAddr(), info.getPort(), id, serviceTitle);

        restTemplate.postForObject(url, "", String.class);
    }
}
