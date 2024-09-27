package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer idRole;
    @Column(name = "title")
    private String title;
    @Column(name = "status")
    private Integer status;
    @Column(name = "content")
    private String content;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "description")
    private String description;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @ManyToMany
    @JoinTable(name = "role_capabilities",
            joinColumns = @JoinColumn(name = "id_role"),
            inverseJoinColumns = @JoinColumn(name="id_capabilities"))
    private Set<Capabilities> capabilities;
    @Column(name = "id_department")
    private Integer idDepartment;
}
