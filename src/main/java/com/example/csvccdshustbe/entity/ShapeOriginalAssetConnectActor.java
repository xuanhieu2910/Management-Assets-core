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
public class ShapeOriginalAssetConnectActor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_s_original_asset_actor")
    private Integer idShapeOriginalAssetActor;
    @Column(name = "id_original")
    private Integer idOriginal;
    @Column(name = "id_asset")
    private Integer idAsset;}
