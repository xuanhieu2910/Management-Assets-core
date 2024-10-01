package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_role")
    private Integer idUserRole;
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "id_role")
    private Integer idRole;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_department")
    private Integer idDepartment;
    @Column(name = "picked")
    private Integer picked;

}
