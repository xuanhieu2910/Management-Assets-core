package com.example.csvccdshustbe.response.curentUsage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllCurrentUsageResponse {

    @JsonProperty("id_current_usage")
    private Integer idCurrentUsage;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
}
