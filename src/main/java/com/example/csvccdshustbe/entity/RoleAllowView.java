package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role_allow_view")
public class RoleAllowView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role_allow_view")
    private Integer idRoleAllowView;
    @Column(name = "id_role")
    private Integer idRole;
    @Column(name = "allow_view")
    private Integer allowView;
}
