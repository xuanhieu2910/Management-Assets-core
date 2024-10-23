package com.example.csvccdshustbe.dto.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDocumentAssetDto {
    private String codeTypeProcess;
    private String nameTypeProcess;
    private String codeDocument;
    private String nameUserCreate;
    private Integer status;
    private String timeCreated;
    private String description;
    private String codeDepartment;
    private String nameDepartment;
}
