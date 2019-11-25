package ru.sberbank.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.entity.Task;
import ru.sberbank.crm.service.TaskService;

@RestController(value = "/tasks")
public class TaskController {

    @Autowired
    TaskService service;

    @PostMapping(value = "/tasks")
    public void sendTaskToRecipient(@RequestBody Task task, @RequestParam String recipient) {
        service.sendTaskToRecipient(task, recipient);
    }
}
