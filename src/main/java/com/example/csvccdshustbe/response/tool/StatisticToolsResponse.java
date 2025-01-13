package com.example.csvccdshustbe.response.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticToolsResponse {

    @JsonProperty("sum_quantity_tools")
    private int sumQuantityTools = 0;
    @JsonProperty("sum_price")
    private String sumPrice = "0";

}
