package com.example.csvccdshustbe.response.typeProcess;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllTypeProcessResponse {

    @JsonProperty("id_type_process")
    private Integer idTypeProcess;
    @JsonProperty("code_type_process")
    private String codeTypeProcess;
    @JsonProperty("name_type_process")
    private String nameTypeProcess;
}
