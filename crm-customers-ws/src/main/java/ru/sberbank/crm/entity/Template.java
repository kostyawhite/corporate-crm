package ru.sberbank.crm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "graph_nodes")
    private String graphNodes;

    @Column(name = "graph_edges")
    private String graphEdges;

    @Override
    public String toString() {
        return description;
    }
}
