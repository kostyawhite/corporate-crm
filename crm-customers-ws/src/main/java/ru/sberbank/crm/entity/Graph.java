package ru.sberbank.crm.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "graphs")
public class Graph {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "graph_nodes")
    private String graphNodes;

    @Column(name = "graph_edges")
    private String graphEdges;
}
