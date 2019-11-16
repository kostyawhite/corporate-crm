package ru.crm.ui.entity;

import javax.persistence.*;

@Entity
@Table (name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String document;
    private String resolver;
    private int status;

    public Task() {
    }

    public Task(String name, String document, String resolver, int status) {
        this.name = name;
        this.document = document;
        this.resolver = resolver;
        this.status = status;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public String getText() {
        return document;
    }

    public void setText(String document) {
        this.document = document;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
