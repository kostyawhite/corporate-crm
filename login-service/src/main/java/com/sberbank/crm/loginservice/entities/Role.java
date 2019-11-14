package com.sberbank.crm.loginservice.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(name = "roles_uk",
                columnNames = "role")
})
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(name = "role", length = 30, nullable = false)
    private String role;

    @Column(name = "description", length = 32, nullable = false)
    private String description;
}
