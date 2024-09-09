package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "s_original_asset_transfer")
public class ShapeOriginalAssetTransfer implements IOriginal{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_s_original_asset_transfer")
    private Integer idShapeOriginalAssetTransfer;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "value_buy")
    private Double valueBuy;
    @Column(name = "value_work")
    private Double valueWork;
    @Column(name = "value_recall_work")
    private Double valueRecallWork;
    @Column(name = "value_tax")
    private Double valueTax;
    @Column(name = "value_other")
    private Double valueOther;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;
}
