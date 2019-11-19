package ru.sberbank.crm.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.crm.models.TaskModel;
import ru.sberbank.crm.repositories.TaskRepository;
import ru.sberbank.crm.services.TaskService;
import ru.sberbank.crm.shared.TaskDto;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @RequestMapping("/tasks")
    public List<TaskModel> getAllTasks() {
        return taskRepository.findAll();
    }

    @RequestMapping(path = "/tasks", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public TaskModel createTask(@RequestBody TaskModel taskModel) {

        TaskModel returnValue = new TaskModel();

        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(taskModel, taskDto);

        TaskDto createdTask = taskService.createTask(taskDto);
        BeanUtils.copyProperties(createdTask, returnValue);

        return returnValue;
    }

    @RequestMapping("/tasks/{id}")
    public TaskModel findTaskById(@PathVariable Integer id) {
        return taskRepository.findTaskById(id);
    }

}
