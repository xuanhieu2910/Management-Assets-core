package com.example.csvccdshustbe.dto.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindDetailsToolDto {
    private String name;
    private String codeTool;
    private String codeToolCategory;
    private Integer idToolCategory;
    private String nameToolCategory;
    private Integer idParent;
    private Integer quantity;
    private String value;
    private Integer idDepartment;
    private String nameDepartment;
    private Integer idLocation;
    private String nameLocation;
    private Integer isIncrease;
    private Integer isDecrease;
    private Integer statusUse;
    private Integer idUserUse;
    private String nameUserUse;
}
