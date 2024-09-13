package com.example.csvccdshustbe.dto.unit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintUnitDto {

    @JsonProperty("id_unit")
    private Integer idUnit;
    @JsonProperty("name")
    private String nameUnit;
}
