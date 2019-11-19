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
    private Integer id;

    @Column(name = "department_id")
    private Integer department_id;

    @Column(name = "next_department_id")
    private Integer next_department_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public Integer getNext_department_id() {
        return next_department_id;
    }

    public void setNext_department_id(Integer next_department_id) {
        this.next_department_id = next_department_id;
    }

}
