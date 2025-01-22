package com.example.csvccdshustbe.dto.document.tool;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDocumentToolDto {

    private Integer idProcess;
    private String codeDocument;
    private Integer idDepartment;
    private String codeDepartment;
    private String nameDepartment;
    private Integer idUserCreate;
    private String fullNameUser;
    private String nameUserCreate;
    private Integer status;
    private Long timeCreated;
    private Long timeModified;
    private String timeDocument;
    private String timeIncrease;
    private String description;
}
