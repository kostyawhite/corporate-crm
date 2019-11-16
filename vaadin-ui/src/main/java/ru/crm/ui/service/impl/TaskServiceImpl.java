package ru.crm.ui.service.impl;

import org.springframework.stereotype.Service;
import ru.crm.ui.entity.Task;
import ru.crm.ui.repository.TaskRepository;
import ru.crm.ui.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task addTask(Task task) {
        Task savedTask = taskRepository.saveAndFlush(task);

        return savedTask;
    }

    @Override
    public void delete(long id) {
        taskRepository.delete(id);
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Task getByName(String name) {
        return taskRepository.findByName(name);
    }

    @Override
    public Task editTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
