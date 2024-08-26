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
public class ShapeOriginalAssetTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_s_original_asset_transfer")
    private int idShapeOriginalAssetTransfer;
    @Column(name = "id_original")
    private int idOriginal;
    @Column(name = "id_asset")
    private int idAsset;
    @Column(name = "value_buy")
    private double valueBuy;
    @Column(name = "value_work")
    private double valueWork;
    @Column(name = "value_recall_work")
    private double valueRecallWork;
    @Column(name = "value_tax")
    private double valueTax;
    @Column(name = "value_other")
    private double valueOther;
}
