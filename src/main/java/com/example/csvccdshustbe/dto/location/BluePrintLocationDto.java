package com.example.csvccdshustbe.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintLocationDto {
    @JsonProperty("id_location")
    private Integer idLocation;
    @JsonProperty("name")
    private String nameLocation;
}
