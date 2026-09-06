package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset_modules")
public class AssetModules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset_module")
    private Integer idAssetModule;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "id_module")
    private Integer idModule;
    @Column(name = "id_instance")
    private Integer idInstance;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
