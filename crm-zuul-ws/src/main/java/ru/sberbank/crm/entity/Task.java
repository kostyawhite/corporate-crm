package ru.sberbank.crm.entity;

import lombok.Data;

@Data
public class Task {

    private Long id;
    private Long templateId;
    private String title;
    private String description;
    private String text;
}
