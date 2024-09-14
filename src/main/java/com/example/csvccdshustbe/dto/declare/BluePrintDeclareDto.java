package com.example.csvccdshustbe.dto.declare;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintDeclareDto {

    @JsonProperty("type_declare")
    private String typeDeclare;
    @JsonProperty("id_declare")
    private Integer idDeclare;
    @JsonProperty("name_declare")
    private String nameDeclare;
    @JsonProperty("id_instance")
    private Integer idInstance;
}
