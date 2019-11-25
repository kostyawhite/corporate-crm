package ru.sberbank.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.crm.entity.TaskState;
import ru.sberbank.crm.exception.TaskNotFoundException;
import ru.sberbank.crm.repository.TaskStateRepository;

import java.util.List;

@Service
public class TaskStateService {

    @Autowired
    TaskStateRepository repository;

    public void saveTask(TaskState taskState) {
        repository.save(taskState);
    }

    public void updateTaskState(Long id, String current) {
        TaskState taskState = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));

        taskState.setPreviousDepartment(taskState.getCurrentDepartment());
        taskState.setCurrentDepartment(current);

        repository.save(taskState);

    }

    public List<TaskState> getAllTasks() {
        return repository.findAll();
    }
}