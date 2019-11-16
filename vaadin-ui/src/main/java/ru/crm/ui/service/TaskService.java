package ru.crm.ui.service;

import ru.crm.ui.entity.Task;

import java.util.List;

public interface TaskService {

    Task addTask(Task task);
    void delete(long id);
    Task getById(Long id);
    Task getByName(String name);
    Task editTask(Task bank);
    List<Task> getAll();
}
