package com.example.csvccdshustbe.entity;

//kien 26-8
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "s_original_asset_invest")
public class ShapeOriginalAssetInvest implements IOriginal{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_s_original_asset_invest")
    private Integer idShapeOriginalAssetInvest;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "value_buy")
    private Double valueBuy;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;

}
