package ru.sberbank.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.crm.entity.Graph;
import ru.sberbank.crm.exception.GraphNotFound;
import ru.sberbank.crm.repository.GraphRepository;

@Service
public class GraphService {

    @Autowired
    GraphRepository repository;

    public Graph getGraphById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new GraphNotFound("Граф не найден"));
    }
}
