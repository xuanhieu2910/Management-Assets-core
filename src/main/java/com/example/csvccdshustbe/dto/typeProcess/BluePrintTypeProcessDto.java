package com.example.csvccdshustbe.dto.typeProcess;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintTypeProcessDto {
    @JsonProperty("id_type_process")
    private Integer idTypeProcess;
    @JsonProperty("code")
    private String codeTypeProcess;
    @JsonProperty("name")
    private String nameTypeProcess;
}
