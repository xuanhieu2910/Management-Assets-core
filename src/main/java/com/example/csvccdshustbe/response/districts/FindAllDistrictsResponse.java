package com.example.csvccdshustbe.response.districts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDistrictsResponse {

    @JsonProperty("code_district")
    private String codeDistricts;
    @JsonProperty("code_province")
    private String codeProvince;
    @JsonProperty("name_district")
    private String nameDistrict;
}
