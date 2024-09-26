package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role_allow_switch")
public class RoleAllowSwitch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role_allow_switch")
    private Integer idRoleAllowSwitch;
    @Column(name = "id_role")
    private Integer idRole;
    @Column(name = "allow_switch")
    private Integer allowSwitch;
}
