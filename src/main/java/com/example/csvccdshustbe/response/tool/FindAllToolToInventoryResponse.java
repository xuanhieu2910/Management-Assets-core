package com.example.csvccdshustbe.response.tool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllToolToInventoryResponse {

    @JsonProperty("id_tool")
    private Integer idTool;
    @JsonProperty("code_tool")
    private String codeTool;
    @JsonProperty("name_tool")
    private String nameTool;
    @JsonProperty("name_tool_category")
    private String nameToolCategory;
    @JsonProperty("code_tool_category")
    private String codeToolCategory;
    @JsonProperty("id_tool_category")
    private Integer idToolCategory;
    @JsonProperty("code_department")
    private String codeDepartment;
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("name_department")
    private String nameDepartment;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("quantity_increase_current")
    private Integer quantityIncreaseCurrent;
    @JsonProperty("quantity_decrease_current")
    private Integer quantityDecreaseCurrent;
    @JsonProperty("quantity_to_inventory")
    private Integer quantityToInventory;
    @JsonProperty("value")
    private String value;
    @JsonProperty("name_location")
    private String nameLocation;
    @JsonProperty("id_location")
    private Integer idLocation;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("status_use")
    private Integer statusUse;
    @JsonProperty("full_name")
    private String fullName;

}
