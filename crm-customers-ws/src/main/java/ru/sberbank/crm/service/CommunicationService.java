package ru.sberbank.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    private final String routerRootUrl = "http:/routerIP:routerPort";

    @Autowired
    private RestTemplate restTemplate;

    public List<Template> getTemplatesFromRouter() {
        // TODO update path
        /*
        String routerRequestUrl = String.format("%s/path", routerRootUrl);

        TemplateList response = restTemplate.getForObject(
                routerRequestUrl,
                TemplateList.class);

        if (response != null) {
            return response.getTemplatesList();
        } else {
            return Collections.emptyList();
        }
        */

        List<Template> templates = new ArrayList<>();
        Template template = new Template();
        template.setId(1L);
        template.setDescription("Шаблон 1");
        templates.add(template);
        return templates;
    }

    public List<Department> getDepartmentsFromRouter(Long selfDepartmentId) {
        // TODO update path
        /*
        String routerRequestUrl = String.format("%s/path/%d", routerRootUrl, selfDepartmentId);

        DepartmentList response = restTemplate.getForObject(
                routerRequestUrl,
                DepartmentList.class);

        if (response != null) {
            return response.getDepartmentList();
        } else {
            return Collections.emptyList();
        }
        */

        List<Department> departments = new ArrayList<>();
        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("ws-department");
        department1.setDescription("Отдел 1");
        departments.add(department1);

        return departments;
    }

    public void sendTaskToDepartmentViaRouter(Task task, String recipientDepartmentName) {
        // TODO update path
        /*
        String routerRequestUrl = String.format("%s/path/%d", routerRootUrl, recipientDepartmentName);

        restTemplate.postForObject(task, String.class);
        */

        System.out.println(task.getTitle() + " отправлена " + recipientDepartmentName);
    }
}
