package com.example.csvccdshustbe.response.positionName;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllPositionNameResponse {
    @JsonProperty("id_position_name")
    private Integer idPositionName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("status")
    private Integer status;
}
