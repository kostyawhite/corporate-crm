package ru.sberbank.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sberbank.crm.models.TaskModel;
import ru.sberbank.crm.repositories.TaskRepository;
import ru.sberbank.crm.services.TaskService;

@Component
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public TaskModel createTask() {

        TaskModel taskModel = new TaskModel();
        taskModel.setTemplate_id(2);
        taskModel.setTitle("task 2");
        taskModel.setDescription("Some description 2");
        taskModel.setFile("task2.txt");

        taskRepository.save(taskModel);

        return taskModel;
    }

}
