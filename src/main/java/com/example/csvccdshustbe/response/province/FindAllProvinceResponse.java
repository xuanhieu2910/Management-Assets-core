package com.example.csvccdshustbe.response.province;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllProvinceResponse {

    @JsonProperty("code_province")
    private String codeProvince;
    @JsonProperty("name_province")
    private String nameProvince;
}
