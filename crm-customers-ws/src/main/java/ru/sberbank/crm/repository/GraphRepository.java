package ru.sberbank.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.crm.entity.Graph;

@Repository
public interface GraphRepository extends JpaRepository<Graph, Long> {
}
