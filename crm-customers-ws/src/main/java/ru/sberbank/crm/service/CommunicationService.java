package ru.sberbank.crm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.crm.entity.Department;
import ru.sberbank.crm.entity.Task;
import ru.sberbank.crm.entity.Template;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunicationService {
    // TODO update root
//    private final String routerRootUrl = "http:/routerIP:routerPort";
    private final String routerRootUrl = "http://192.168.100.5:8763";

    @Autowired
    private RestTemplate restTemplate;

    public List<Template> getTemplatesFromRouter() {
        // TODO update path

        String routerRequestUrl = String.format("%s/templates/descriptions", routerRootUrl);

        ResponseEntity<Object[]> response = restTemplate .getForEntity(
                routerRequestUrl,
                Object[].class
        );

        Object[] objects = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        List<Template> templates = mapper.convertValue(objects, new TypeReference<List<Template>>() {});

        return templates;
    }

    public List<Department> getDepartmentsFromRouter() {
        // TODO update path

        String routerRequestUrl = String.format("%s/departments", routerRootUrl);

        ResponseEntity<Object[]> response = restTemplate .getForEntity(
                routerRequestUrl,
                Object[].class
        );

        Object[] objects = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        List<Department> departments = mapper.convertValue(objects, new TypeReference<List<Department>>() {});

        return departments;
    }

    public void sendTaskToDepartmentViaRouter(Task task, String recipientDepartmentName) {
        // TODO update path

        String routerRequestUrl = String.format("%s/path/%d", routerRootUrl, recipientDepartmentName);

        restTemplate.postForObject(task, String.class);

        System.out.println(task.getTitle() + " отправлена " + recipientDepartmentName);
    }
}
