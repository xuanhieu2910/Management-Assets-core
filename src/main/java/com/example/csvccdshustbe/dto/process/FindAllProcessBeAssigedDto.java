package com.example.csvccdshustbe.dto.process;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllProcessBeAssigedDto {

    @JsonProperty("id_process")
    private Integer idProcess;
    @JsonProperty("id_type_process")
    private Integer idTypeProcess;
    @JsonProperty("name_type_process")
    private String nameTypeProcess;
    @JsonProperty("name_process")
    private String nameProcess;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("id_document")
    private Integer idDocument;
    @JsonProperty("name_document")
    private String nameDocument;
    @JsonProperty("code_document")
    private String codeDocument;
}
