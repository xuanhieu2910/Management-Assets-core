package com.example.csvccdshustbe.dto.assetDepreciation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssetDepreciationDto {

    @JsonProperty("id_asset_depreciation")
    private Integer idAssetDepreciation;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("time_started_depreciation")
    private String timeStartedDepreciation;
    @JsonProperty("amount_months_depreciation")
    private Integer amountMonthsDepreciation;
    @JsonProperty("value_depreciation")
    private String valueDepreciation;
    @JsonProperty("type_depreciation")
    private Integer typeDepreciation;
    @JsonProperty("value_type_depreciation")
    private String valueTypeDepreciation;
    @JsonProperty("amount_rest_month_depreciation")
    private Integer amountRestMonthsDepreciation;
    @JsonProperty("cumulative")
    private String cumulative;
    @JsonProperty("rest_value")
    private String restValue;
    @JsonProperty("time_started_wear_tear")
    private String timeStartedWearTear;
    @JsonProperty("time_end_wear_tear")
    private String timeEndWearTear;
    @JsonProperty("type_calculate")
    private Integer typeCalculate;
    @JsonProperty("time_buy")
    private String timeBuy;
    @JsonProperty("time_started_used")
    private String timeStartedUsed;
    @JsonProperty("time_started_increase")
    private String timeStartedIncrease;
    @JsonProperty("time_year_tracking")
    private String timeYearTracking;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;

}
