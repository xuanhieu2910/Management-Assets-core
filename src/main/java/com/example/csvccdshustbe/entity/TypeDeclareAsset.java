package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "type_declare_asset")
public class TypeDeclareAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_declare_asset")
    private Integer idTypeDeclareAsset;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_asset_category")
    private Integer idAssetCategory;
}
