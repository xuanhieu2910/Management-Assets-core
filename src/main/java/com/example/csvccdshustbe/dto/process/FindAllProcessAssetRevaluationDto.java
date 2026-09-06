package com.example.csvccdshustbe.dto.process;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllProcessAssetRevaluationDto {
    private Integer idProcess;
    private String codeDocument;
    private Integer idDepartment;
    private String codeDepartment;
    private String nameDepartment;
    private Integer idUserCreate;
    private String codeUserCreate;
    private String nameUserCreate;
    private Integer status;
    private Long timeCreated;
    private Long timeModified;
    private String timeDocument;
    private String timeRevaluation;
}
