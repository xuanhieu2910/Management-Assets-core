package com.example.csvccdshustbe.response.typeState;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllTypeStateResponse {

    @JsonProperty("id_type_state")
    private Integer idTypeState;
    @JsonProperty("code_type_state")
    private String codeTypeState;
    @JsonProperty("name_type_state")
    private String nameTypeState;
}
