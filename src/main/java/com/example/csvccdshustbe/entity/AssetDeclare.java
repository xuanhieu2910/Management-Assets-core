package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset_declare")
public class AssetDeclare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset_declare")
    private Integer idAssetDeclare;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "id_declare")
    private Integer idDeclare;
    @Column(name = "id_instance")
    private Integer idInstance;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
