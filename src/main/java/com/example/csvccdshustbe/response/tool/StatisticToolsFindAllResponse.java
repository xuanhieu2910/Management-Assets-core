package com.example.csvccdshustbe.response.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticToolsFindAllResponse {
    @JsonProperty("total_distribution")
    private Integer totalDistribution = 0;
    @JsonProperty("total_parent")
    private Integer totalParent = 0;
}
