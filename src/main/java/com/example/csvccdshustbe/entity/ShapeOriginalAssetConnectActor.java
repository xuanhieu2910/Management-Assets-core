package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "s_original_asset_connect_actor")
public class ShapeOriginalAssetConnectActor implements IOriginal{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_s_original_asset_connect_actor")
    private Integer idShapeOriginalAssetConnectActor;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
