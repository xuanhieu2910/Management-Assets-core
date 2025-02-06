package com.example.csvccdshustbe.response.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsAssetFindAllResponse {
    @JsonProperty("total_single")
    private Integer totalSingle = 0;
    @JsonProperty("total_distribution")
    private Integer totalDistribution = 0;
    @JsonProperty("total_lot")
    private Integer totalLot = 0;
}
