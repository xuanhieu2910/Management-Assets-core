package com.example.csvccdshustbe.dto.process;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllProcessAssetDto {
    private Integer idProcess;
    private String codeDocument;
    private Integer idDepartment;
    private String codeDepartment;
    private String nameDepartment;
    private Integer idUserCreate;
    private String codeUserCreate;
    private String nameUserCreate;
    private Integer status;
    private String timeCreated;
    private String timeModified;
    private String timeDocument;
    private String timeIncrease;
}
