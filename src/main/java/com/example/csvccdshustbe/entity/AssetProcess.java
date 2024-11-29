package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset_process")
public class AssetProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset_process")
    private Integer idAssetProcess;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "id_process")
    private Integer idProcess;
    @Column(name = "id_type_process")
    private Integer idTypeProcess;
    @Column(name = "status")
    private Integer status;
    @Column(name = "value")
    private String value;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "id_user_created")
    private Integer idUserCreated;
    @Column(name = "id_user_modified")
    private Integer idUserModified;
}
