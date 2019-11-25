package ru.sberbank.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.crm.entity.TaskState;

@Repository
public interface TaskStateRepository extends JpaRepository<TaskState, Long> {

}
