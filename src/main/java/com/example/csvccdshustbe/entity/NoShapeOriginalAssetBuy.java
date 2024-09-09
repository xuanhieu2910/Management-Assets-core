package com.example.csvccdshustbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ns_original_asset_buy")
public class NoShapeOriginalAssetBuy implements IOriginal{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ns_original_asset_buy")
    private Integer idNoShapeOriginalAssetBuy;
    @Column(name = "id_method_buy_asset")
    private Integer idMethodBuyAsset;
    @Column(name = "id_type_buy_asset")
    private Integer idTypeBuyAsset;
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
