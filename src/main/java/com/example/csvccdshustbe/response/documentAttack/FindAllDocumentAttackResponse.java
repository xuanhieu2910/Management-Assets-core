package com.example.csvccdshustbe.response.documentAttack;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDocumentAttackResponse {
    @JsonProperty("id_document_attack")
    private Integer idDocumentAttack;
    @JsonProperty("name")
    private String name;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("date_determination_document")
    private String dateDeterminationDocument;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("code")
    private String code;
    @JsonProperty("id_department")
    private Integer idDepartment;
}
