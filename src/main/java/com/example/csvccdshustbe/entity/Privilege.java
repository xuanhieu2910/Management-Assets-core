//package com.example.csvccdshustbe.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Set;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(name = "privilege")
//public class Privilege {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_privilege")
//    private Integer idPrivilege;
//    @Column(name = "title")
//    private String title;
//    @Column(name = "status")
//    private Integer status;
//    @Column(name = "short_name")
//    private String shortName;
//    @Column(name = "description")
//    private String description;
//    @Column(name = "slug")
//    private String slug;
//    @Column(name = "time_created")
//    private String timeCreated;
//    @Column(name = "time_modified")
//    private String timeModified;
//    @ManyToMany(mappedBy = "privileges")
//    private Set<Role> roleSet;
//
//}
