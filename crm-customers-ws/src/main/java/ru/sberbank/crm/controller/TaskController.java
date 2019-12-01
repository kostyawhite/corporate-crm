package ru.sberbank.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.entity.Task;
import ru.sberbank.crm.service.CommunicationService;
import ru.sberbank.crm.service.TaskService;
import ru.sberbank.crm.util.TaskBroadcaster;

@RestController(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommunicationService communicationService;

    @PostMapping(value = "/send")
    public void addTask(@RequestBody Task task) {
        taskService.saveTask(task);
        TaskBroadcaster.broadcast(taskService.getTaskById(task.getId()));

        communicationService.sendTaskStatusChange(task.getId());
    }
}
