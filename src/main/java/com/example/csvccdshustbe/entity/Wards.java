package com.example.csvccdshustbe.entity;

//Kien 26-8
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity

@Table(name = "wards")
public class Wards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_ward")
    private Integer idWard;
    @Column(name="code")
    private String code;
    @Column(name="name")
    private String name;
    @Column(name="name_en")
    private String nameEn;
    @Column(name="full_name")
    private String fullName;
    @Column(name="full_name_en")
    private String fullNameEn;
    @Column(name="code_name")
    private String codeName;
    @Column(name="district_code")
    private String districtCode;
}
