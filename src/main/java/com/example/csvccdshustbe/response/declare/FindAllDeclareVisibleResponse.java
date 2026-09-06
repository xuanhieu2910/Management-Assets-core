package com.example.csvccdshustbe.response.declare;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDeclareVisibleResponse {

    @JsonProperty("id_declare")
    private Integer idDeclare;
    @JsonProperty("name_declare")
    private String nameDeclare;
    @JsonProperty("hard_code")
    private String hardCode;
}
