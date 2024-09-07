package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset_current_usage")
public class AssetCurrentUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset_current_usage")
    private Integer idAssetCurrentUsage;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "id_current_usage")
    private Integer idCurrentUsage;
    @Column(name = "time_created")
    private String timeCreated;

}
