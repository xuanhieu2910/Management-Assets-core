package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset")
public class Asset {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "name")
    private String name;
    @Column(name = "code_asset")
    private String codeAsset;
    @Column(name = "id_asset_category")
    private Integer idAssetCategory;
    @Column(name = "id_document_attack")
    private Integer idDocumentAttack;
    @Column(name = "id_department")
    private Integer idDepartment;
    @Column(name = "id_location")
    private Integer idLocation;
    @Column(name = "id_unit")
    private Integer idUnit;
    @Column(name = "id_original")
    private Integer idOriginal;
    @Column(name = "id_projects")
    private Integer idProjects;
    @Column(name = "purpose")
    private String purpose;
    @Column(name = "notes")
    private String notes;
    @Column(name = "file_attack")
    private String fileAttack;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_department_default")
    private Integer idDepartmentDefault;
    @Column(name = "id_level_type_asset")
    private Integer idLevelTypeAsset;
    @Column(name = "id_user_created")
    private Integer idUserCreated;
    @Column(name = "id_user_modified")
    private Integer idUserModified;
    @Column(name = "describe")
    private String describe;
    @Column(name = "quantity")
    private Integer quantity;

}
