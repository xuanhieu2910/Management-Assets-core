package com.example.csvccdshustbe.response.units;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllUnitsByCodeAssetCategoryResponse {

    @JsonProperty("id_unit")
    private Integer idUnit;
    @JsonProperty("name")
    private String name;
}
