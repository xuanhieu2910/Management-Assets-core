package com.example.csvccdshustbe.response.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllToolResponseToDecrease {
    @JsonProperty("code_tool")
    private String codeTool;
    @JsonProperty("name_tool")
    private String nameTool;
    @JsonProperty("name_tool_category")
    private String nameToolCategory;
    @JsonProperty("code_tool_category")
    private String codeToolCategory;
    @JsonProperty("code_department")
    private String codeDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("id_tool")
    private Integer idTool;
    @JsonProperty("salt")
    private String salt;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("value")
    private String value;
    @JsonProperty("quantity_increase_current")
    private Integer quantityIncreaseCurrent;
    @JsonProperty("quantity_decrease_current")
    private Integer quantityDecreaseCurrent;
    @JsonProperty("status_use")
    private Integer statusUse;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("name_location")
    private String nameLocation;
}
