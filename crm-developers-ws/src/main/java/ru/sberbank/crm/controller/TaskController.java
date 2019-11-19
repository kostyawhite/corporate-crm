package ru.sberbank.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.entity.Task;
import ru.sberbank.crm.service.TaskService;
import ru.sberbank.crm.util.Broadcaster;

@RestController(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService service = new TaskService();

    @PostMapping(value = "/send")
    public void addTask(@RequestBody Task task) {
        service.saveTask(task);
        Broadcaster.broadcast(service.getTaskById(task.getId()));
    }
}
