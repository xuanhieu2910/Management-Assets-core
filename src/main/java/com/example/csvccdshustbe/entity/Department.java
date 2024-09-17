package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_department")
    private Integer idDepartment;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "description")
    private String description;
    @Column(name = "parent")
    private Integer parent;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "status")
    private Integer status;
}
