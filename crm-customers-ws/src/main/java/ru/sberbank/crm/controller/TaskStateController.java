package ru.sberbank.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.service.TaskStateService;

@RestController(value = "/tasks-state")
public class TaskStateController {

    @Autowired
    private TaskStateService service;

    @PostMapping(value = "/tasks-state")
    public void updateTaskState(@RequestParam String id, @RequestParam String current) {
        service.updateTaskState(Long.parseLong(id), current);

        // todo graph update
    }
}
