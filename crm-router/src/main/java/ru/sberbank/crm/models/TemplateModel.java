package ru.sberbank.crm.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "templates")
public class TemplateModel implements Serializable {

    private static final long serialVersionUID = 6620368094088902280L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "department_id")
    private Long department_id;

    @Column(name = "next_department_id")
    private Long next_department_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }

    public Long getNext_department_id() {
        return next_department_id;
    }

    public void setNext_department_id(Long next_department_id) {
        this.next_department_id = next_department_id;
    }

}
