package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset_original_of_formation")
public class AssetOriginalOfFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset_origin_of_formation")
    private Integer idAssetOriginOfFormation;
    @Column(name = "id_original_of_formation")
    private Integer idOriginalOfFormation;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
    @Column(name = "value")
    private String value;
}
