package com.example.csvccdshustbe.response.originalOfFormationTool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllOriginalOfFormationToolVisibleResponse {

    @JsonProperty("id_original_of_formation_tool")
    private Integer idOriginalOfFormationTool;
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
}
