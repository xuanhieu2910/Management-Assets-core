package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ns_original_asset_rent_land")
public class NoShapeOriginalAssetRentLand implements IOriginal{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ns_original_asset_rent_land")
    private Integer idNoShapeOriginalAssetRentLand;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "value_rent")
    private Double valueRent;
    @Column(name = "value_work")
    private Double valueWork;
    @Column(name = "value_other")
    private Double valueOther;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
