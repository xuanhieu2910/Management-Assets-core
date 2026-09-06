package com.example.csvccdshustbe.response.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsAssetCategoryStatusUse {
    @JsonProperty("total_not_using")
    private Integer totalNotUsing = 0;
    @JsonProperty("total_using")
    private Integer totalUsing = 0;
    @JsonProperty("total_decrease")
    private Integer totalDecrease = 0;
}
