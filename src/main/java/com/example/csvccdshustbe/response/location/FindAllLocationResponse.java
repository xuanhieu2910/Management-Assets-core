package com.example.csvccdshustbe.response.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllLocationResponse {
    @JsonProperty("id_location")
    private Integer idLocation;
    @JsonProperty("name")
    private String name;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("id_department")
    private Integer idDepartment;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("path")
    private String path;
    @JsonProperty("name_parent")
    private String nameParent;
    @JsonProperty("visible")
    private Integer visible;
    @JsonProperty("name_department")
    private String nameDepartment;
}
