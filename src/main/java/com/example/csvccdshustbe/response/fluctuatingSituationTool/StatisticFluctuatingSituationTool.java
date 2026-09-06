package com.example.csvccdshustbe.response.fluctuatingSituationTool;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticFluctuatingSituationTool {

    @JsonProperty("total_declare")
    private Integer totalDeclare = 0;
    @JsonSetter("total_increase")
    private Integer totalIncrease = 0;
    @JsonProperty("total_decrease")
    private Integer totalDecrease = 0;
}
