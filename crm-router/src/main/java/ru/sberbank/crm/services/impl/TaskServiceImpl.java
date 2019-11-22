package ru.sberbank.crm.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sberbank.crm.models.TaskModel;
import ru.sberbank.crm.repositories.TaskRepository;
import ru.sberbank.crm.services.TaskService;
import ru.sberbank.crm.shared.TaskDto;

@Component
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public TaskDto createTask(TaskDto task) {

        TaskModel taskModel = new TaskModel();
        BeanUtils.copyProperties(task, taskModel);

        taskRepository.save(taskModel);

        TaskDto returnValue = new TaskDto();
        BeanUtils.copyProperties(task, returnValue);

        return returnValue;
    }

}
