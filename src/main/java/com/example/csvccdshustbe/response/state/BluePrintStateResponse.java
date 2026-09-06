package com.example.csvccdshustbe.response.state;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintStateResponse {

    @JsonProperty("id_state")
    private Integer idState;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("code_type_state")
    private String codeTypeState;
    @JsonProperty("id_type_state")
    private Integer idTypeState;
    @JsonProperty("name_type_state")
    private String nameTypeState;

}
