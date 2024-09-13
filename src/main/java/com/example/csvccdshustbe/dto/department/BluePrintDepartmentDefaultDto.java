package com.example.csvccdshustbe.dto.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BluePrintDepartmentDefaultDto {
    @JsonProperty("name")
    private String nameDefaultDepartment;
    @JsonProperty("id_default_department")
    private Integer idDefaultDepartment;
}
