package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset_original")
public class AssetOriginal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset_original")
    private Integer idAssetOriginal;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "id_original")
    private Integer idOriginal;
    @Column(name = "id_instance")
    private Integer idInstance;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
