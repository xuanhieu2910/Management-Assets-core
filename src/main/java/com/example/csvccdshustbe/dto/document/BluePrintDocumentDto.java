package com.example.csvccdshustbe.dto.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintDocumentDto {
    @JsonProperty("code_document")
    private String codeDocument;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_increase")
    private String timeIncrease;
    @JsonProperty("time_document")
    private String timeDocument;
    @JsonProperty("description")
    private String description;
}
