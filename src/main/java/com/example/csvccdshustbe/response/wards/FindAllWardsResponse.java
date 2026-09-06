package com.example.csvccdshustbe.response.wards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllWardsResponse {

    @JsonProperty("code_ward")
    private String codeWards;
    @JsonProperty("name_ward")
    private String nameWards;
    @JsonProperty("code_district")
    private String codeDistricts;
}
