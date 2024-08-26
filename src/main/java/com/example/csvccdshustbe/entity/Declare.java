package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "declare")
public class Declare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_declare")
    private Integer idDeclare;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "visible")
    private Integer visible;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_category")
    private Integer idCategory;

}
