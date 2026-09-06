package com.example.csvccdshustbe.response.originalOfFormation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllOriginalOfFormationResponse {

    @JsonProperty("id_original_of_formation")
    private Integer idOriginalOfFormation;
    @JsonProperty("name")
    private String name;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("visible")
    private Integer visible;
    @JsonProperty("depth")
    private Integer depth;
    @JsonProperty("path")
    private String path;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("name_parent")
    private String nameParent;
    @JsonProperty("code_name")
    private String codeName;
    @JsonProperty("description")
    private String description;
}
