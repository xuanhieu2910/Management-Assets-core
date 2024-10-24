package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document")
    private Integer idDocument;
    @Column(name = "code")
    private String code;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "time_increase")
    private String timeIncrease;
    @Column(name = "time_document")
    private String timeDocument;
    @Column(name = "id_department")
    private Integer idDepartment;
    @Column(name = "description")
    private String description;
    @Column(name = "id_process")
    private Integer idProcess;
}
