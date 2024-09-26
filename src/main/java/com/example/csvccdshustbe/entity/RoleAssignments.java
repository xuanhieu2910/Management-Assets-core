package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role_assignments")
public class RoleAssignments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role_assignments")
    private Integer idRoleAssignments;
    @Column(name = "id_user_role")
    private Integer idUserRole;
    @Column(name = "id_context")
    private Integer idContext;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
