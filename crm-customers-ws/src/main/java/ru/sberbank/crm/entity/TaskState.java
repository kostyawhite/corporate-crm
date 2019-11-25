package ru.sberbank.crm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tasks_state")
public class TaskState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "graph_id")
    private Long graphId;

    @Column(name = "title")
    private String title;

    @Column(name = "previous_department")
    private String previousDepartment;

    @Column(name = "current_department")
    private String currentDepartment;
}
