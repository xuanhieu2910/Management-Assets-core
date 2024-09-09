package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ns_original_asset_use_land")
public class NoShapeOriginalAssetUseLand implements IOriginal{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ns_original_asset_use_land")
    private Integer idNoShapeOriginalAssetUseLand;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "value_buy")
    private Double valueBuy;
    @Column(name = "value_tax")
    private Double valueTax;
    @Column(name = "value_other")
    private Double valueOther;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
