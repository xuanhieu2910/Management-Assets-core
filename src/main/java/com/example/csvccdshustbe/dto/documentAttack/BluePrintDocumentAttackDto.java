package com.example.csvccdshustbe.dto.documentAttack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BluePrintDocumentAttackDto {

    @JsonProperty("id_document_attack")
    private Integer idDocumentAttack;
    @JsonProperty("name")
    private String nameDocumentAttack;

}
