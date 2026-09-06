package com.example.csvccdshustbe.dto.tool;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolDto {

    private Integer idTool;
    private String name;
    private String codeTool;
    private String salt;
    private Integer idToolCategory;
    private String codeToolCategory;
    private String nameToolCategory;
    private Long timeCreated;
    private Long timeModified;
    private Integer idUserCreated;
    private Integer idUserModified;
    private String value;
    private Integer quantity;
    private Integer isIncrease;
    private Integer isDecrease;
    private Integer quantityIncreaseCurrent;
    private Integer quantityDecreaseCurrent;
    private Integer idProcessCurrent;
    private Integer statusProcessCurrent;
    private Integer idTypeProcessCurrent;
    private Integer idDepartmentOriginal;
    private Integer statusUse;
    private Integer parent;
    private Integer idDepartment;
    private String codeDepartment;
    private String nameDepartment;
    private Integer idLocation;
    private String nameLocation;
    private Integer idUserUse;
    private String userName;
    private String yearUse;
    private String price;
    private String fullName;
}
