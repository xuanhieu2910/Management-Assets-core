package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "s_original_asset_evaluate")
public class ShapeOriginalAssetEvaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_s_original_asset_evaluate")
    private Integer idShapeOriginalAssetEvaluate;
    @Column(name = "id_original")
    private Integer idOriginal;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "value_buy")
    private Double valueBuy;
    @Column(name = "value_tax")
    private Double valueTax;
    @Column(name = "value_other")
    private Double valueOther;
}
