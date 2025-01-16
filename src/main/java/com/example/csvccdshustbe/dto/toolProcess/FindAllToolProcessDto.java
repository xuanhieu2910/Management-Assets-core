package com.example.csvccdshustbe.dto.toolProcess;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllToolProcessDto {
    private Integer idTool;
    private String codeTool;
    private String nameTool;
    private Integer idToolCategory;
    private String codeToolCategory;
    private String nameToolCategory;
    private Integer idDepartment;
    private String codeDepartment;
    private String nameDepartment;
    private Integer idLocation;
    private String nameLocation;
    private Long timeCreated;
    private Long timeModified;
    private Integer quantity;
    private Integer parent;
    private String salt;
    private Integer isIncrease;
    private Integer isDecrease;
    private String timeIncrease;
    private String value;
    private Integer statusProcessCurrent;
    private Integer statusUse;
    private String yearUse;
    private Integer idToolProcess;
    private Integer typeTarget;
    private Integer statusToolProcess;

}
