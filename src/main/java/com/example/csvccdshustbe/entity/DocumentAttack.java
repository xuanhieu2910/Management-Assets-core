package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "document_attack")
public class DocumentAttack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document_attack")
    private Integer idDocumentAttack;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private Integer code;
    @Column(name = "id_department")
    private Integer idDepartment;
    @Column(name = "date_determination_document")
    private String dateDeterminationDocument;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
