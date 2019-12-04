package ru.sberbank.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.crm.entity.Task;
import ru.sberbank.crm.exception.TaskNotFoundException;
import ru.sberbank.crm.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public void saveTask(Task task) {
        repository.save(task);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskByTitle(String title) {
        return repository
                .findByTitle(title).orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));
    }

    public Task getTaskById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));
    }
}
