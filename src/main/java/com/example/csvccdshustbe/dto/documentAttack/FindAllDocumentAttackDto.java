package com.example.csvccdshustbe.dto.documentAttack;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDocumentAttackDto {

    private Integer idDocumentAttack;
    private String name;
    private String code;
    private Integer idDepartment;
    private String dateDeterminationDocument;
    private Integer status;
    private String timeCreated;
    private String timeModified;
    private String nameDepartment;
}
