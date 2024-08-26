package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "provinces")
public class Provinces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_province")
    private Integer idProvince;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name="name_en")
    private String nameEn;
    @Column(name="full_name")
    private String fullName;
    @Column(name="full_name_en")
    private String fullNameEn;
    @Column(name="code_name")
    private String codeName;
}
