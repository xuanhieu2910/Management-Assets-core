package com.example.csvccdshustbe.dto.department;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BluePrintDepartmentDto {

    @JsonProperty("code")
    private String codeDepartment;
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("name")
    private String nameDepartment;
}
