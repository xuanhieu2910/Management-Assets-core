package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "capabilities")
public class Capabilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_capability")
    private Integer idCapability;
    @Column(name = "name")
    private String name;
    @Column(name = "cap_type")
    private String capType;
    @Column(name = "context_level")
    private Integer contextLevel;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @ManyToMany(mappedBy = "capabilities")
    private Set<Role> roleSet;
    @Column(name = "component")
    private String component;
}
