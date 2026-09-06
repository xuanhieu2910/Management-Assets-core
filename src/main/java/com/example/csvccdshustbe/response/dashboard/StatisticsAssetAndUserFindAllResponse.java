package com.example.csvccdshustbe.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsAssetAndUserFindAllResponse {
    @JsonProperty("total_asset")
    private Integer totalAsset = 0;
    @JsonProperty("total_user")
    private Integer totalUser = 0;
    @JsonProperty("total_ground")
    private Integer totalGround = 0;
    @JsonProperty("total_house")
    private Integer totalHouse = 0;
    @JsonProperty("total_architecture")
    private Integer totalArchitecture = 0;
    @JsonProperty("total_car")
    private Integer totalCar = 0;
    @JsonProperty("total_other_vehicle_transport")
    private Integer totalOtherVehicleTransport = 0;
    @JsonProperty("total_machine")
    private Integer totalMachine = 0;
    @JsonProperty("total_tree_and_animal")
    private Integer totalTreeAndAnimal = 0;
    @JsonProperty("total_other_asset")
    private Integer totalOtherAsset = 0;
    @JsonProperty("total_invisible_asset")
    private Integer totalInvisibleAsset= 0;
    @JsonProperty("total_invisible_asset_special")
    private Integer totalInvisibleAssetSpecial= 0;
    @JsonProperty("total_construction")
    private Integer totalConstruction = 0;
    @JsonProperty("total_electric_construction")
    private Integer totalElectricConstruction = 0;
}
