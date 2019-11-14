package com.sberbank.crm.loginservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "users_name_uk",
                columnNames = "name"),
        @UniqueConstraint(name = "users_email_uk",
        columnNames = "email")
})
@Data
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name", length = 36, nullable = false)
    private String name;

    @Column(name = "email", length = 64, nullable = false)
    private String email;

    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "activation_code", length = 36)
    private String activationCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
