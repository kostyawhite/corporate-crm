package ru.sberbank.crm.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "templates_description")
public class Template {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;
}
