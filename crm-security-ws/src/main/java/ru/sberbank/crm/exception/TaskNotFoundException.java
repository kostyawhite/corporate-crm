package ru.sberbank.crm.exception;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(String msg) {
        super(msg);
    }
}
