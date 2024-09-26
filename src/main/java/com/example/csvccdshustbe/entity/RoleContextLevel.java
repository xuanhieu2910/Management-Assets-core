package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role_context_level")
public class RoleContextLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role_context_level")
    private Integer idRoleContextLevel;
    @Column(name = "id_role")
    private Integer idRole;
    @Column(name = "context_level")
    private Integer contextLevel;
}
