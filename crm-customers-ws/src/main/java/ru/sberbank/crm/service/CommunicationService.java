package ru.sberbank.crm.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.crm.entity.Department;
import ru.sberbank.crm.entity.DepartmentEdge;
import ru.sberbank.crm.entity.Task;
import ru.sberbank.crm.entity.Template;
import ru.sberbank.crm.wrapper.DepartmentEdgeList;
import ru.sberbank.crm.wrapper.DepartmentList;
import ru.sberbank.crm.wrapper.TemplateList;

import java.util.*;

@Service
public class CommunicationService {

    private final String routerEurekaName = "crm-zuul-ws";
    private final String serviceTitle = "Отдел клиентов";

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

    public List<Department> getDepartmentsFromRouter() {

        Application application = eurekaClient.getApplication(routerEurekaName);
        InstanceInfo info = application.getInstances().get(0);

        String url = String.format("http://%s:%d/all",
                info.getIPAddr(), info.getPort());
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

    public void sendTemplate(Long templateId, String rawEdges, List<Department> departments) {
        String result = rawEdges;
        for (Department department : departments) {
            result = result.replace(department.getDescription(), department.getId().toString());
        }

        result = result.replace("{\"edges\":[", "")
                .replace("]}", "")
                .replace(":", "")
                .replace("\"", "")
                .replace("source", "")
                .replace("target", "")
                .replace("},{", ";")
                .replace("{", "")
                .replace("}", "");


        List<DepartmentEdge> sendDepartments = new ArrayList<>();
        for (String line : result.split(";")) {
            String[] ids = line.split(",");
            DepartmentEdge departmentEdge = new DepartmentEdge();
            departmentEdge.setId(templateId);
            departmentEdge.setDepartmentId(Long.parseLong(ids[0]));
            departmentEdge.setNextDepartmentId(Long.parseLong(ids[1]));

            sendDepartments.add(departmentEdge);
        }

        DepartmentEdgeList departmentEdgeList = new DepartmentEdgeList();
        departmentEdgeList.setDepartmentsEdgesList(sendDepartments);

        Application application = eurekaClient.getApplication(routerEurekaName);
        InstanceInfo info = application.getInstances().get(0);

        String url = String.format("http://%s:%d/edges",
                info.getIPAddr(), info.getPort());

        restTemplate.postForObject(url, departmentEdgeList, String.class);
    }
}
