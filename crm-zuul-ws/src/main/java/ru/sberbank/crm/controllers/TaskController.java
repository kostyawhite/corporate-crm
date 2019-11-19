package ru.sberbank.crm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.models.TaskModel;
import ru.sberbank.crm.repositories.TaskRepository;
import ru.sberbank.crm.services.TaskService;
import ru.sberbank.crm.services.impl.TaskServiceImpl;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskServiceImpl taskServiceImpl;

    @RequestMapping("/tasks")
    public List<TaskModel> getAllTasks() {
        return taskRepository.findAll();
    }

    @RequestMapping("/tasks/new")
    public TaskModel createTask() {
        return taskServiceImpl.createTask();
    }

    @RequestMapping("/tasks/{id}")
    public TaskModel findTaskById(@PathVariable Integer id) {
        return taskRepository.findTaskById(id);
    }

}
