package com.example.csvccdshustbe.dto.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllocateToolDto {
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("id_location")
    private Integer idLocation;
    @JsonProperty("name_location")
    private String nameLocation;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("status_use")
    private Integer statusUse;
    @JsonProperty("quantity")
    private Integer quantity;


}
