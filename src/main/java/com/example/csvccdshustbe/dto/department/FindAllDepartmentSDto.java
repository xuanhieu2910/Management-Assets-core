package com.example.csvccdshustbe.dto.department;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDepartmentSDto {

    private Integer idDepartment;
    private String name;
    private String code;
    private String shortName;
    private String description;
    private Integer parent;
    private String timeCreated;
    private String timeModified;
    private Integer depth;
    private String path;
    private Integer status;
    private String nameParent;
}
