package com.example.csvccdshustbe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "asset_depreciation")
public class AssetDepreciation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset_depreciation")
    private Integer idAssetDepreciation;
    @Column(name = "id_asset")
    private Integer idAsset;
    @Column(name = "time_started_depreciation")
    private String timeStartedDepreciation;
    @Column(name = "amount_months_depreciation")
    private Integer amountMonthsDepreciation;
    @Column(name = "value_depreciation")
    private String valueDepreciation;
    @Column(name = "type_depreciation")
    private Integer typeDepreciation;
    @Column(name = "value_type_depreciation")
    private String valueTypeDepreciation;
    @Column(name = "amount_rest_months_depreciation")
    private Integer amountRestMonthsDepreciation;
    @Column(name = "cumulative")
    private String cumulative;
    @Column(name = "rest_value")
    private String restValue;
    @Column(name = "time_started_wear_tear")
    private String timeStartedWearTear;
    @Column(name = "time_end_wear_tear")
    private String timeEndWearTear;
    @Column(name = "type_calculate")
    private Integer typeCalculate;
    @Column(name = "time_buy")
    private String timeBuy;
    @Column(name = "time_started_used")
    private String timeStartedUsed;
    @Column(name = "time_started_increase")
    private String timeStartedIncrease;
    @Column(name = "time_year_tracking")
    private String timeYearTracking;
    @Column(name = "time_created")
    private String timeCreated;
    @Column(name = "time_modified")
    private String timeModified;

}
