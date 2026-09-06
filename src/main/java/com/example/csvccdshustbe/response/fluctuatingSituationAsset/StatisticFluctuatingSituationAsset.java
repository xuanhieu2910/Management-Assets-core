package com.example.csvccdshustbe.response.fluctuatingSituationAsset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticFluctuatingSituationAsset {


    @JsonProperty("total_declare")
    private Integer totalDeclare = 0;
    @JsonSetter("total_increase")
    private Integer totalIncrease = 0;
    @JsonProperty("total_decrease")
    private Integer totalDecrease = 0;
}
