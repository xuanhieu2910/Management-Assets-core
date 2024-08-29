package com.example.csvccdshustbe.dto.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDepartmentByCodeAndVisibleDto {
    private Integer idDepartment;
    private String name;
    private String code;
    private String shortName;
    private String description;
    private Integer parent;
    private String timeCreated;
    private String timeModified;
    private Integer depth;
    private Integer status;
    private String path;
}
