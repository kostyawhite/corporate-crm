package ru.sberbank.crm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.crm.models.TaskModel;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, Integer> {

    List<TaskModel> findAll();
    TaskModel findTaskById(Integer id);

}
