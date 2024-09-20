package com.example.csvccdshustbe.response.wards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllWardsResponse {

    @JsonProperty("code_wards")
    private String codeWards;
    @JsonProperty("name_wards")
    private String nameWards;
    @JsonProperty("code_districts")
    private String codeDistricts;
}
