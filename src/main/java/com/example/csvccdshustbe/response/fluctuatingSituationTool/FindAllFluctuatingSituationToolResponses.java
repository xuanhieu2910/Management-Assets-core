package com.example.csvccdshustbe.response.fluctuatingSituationTool;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllFluctuatingSituationToolResponses {
    @JsonProperty("id_fluctuating_situation_tool")
    private Integer idFluctuatingSituationTool;
    @JsonProperty("id_tool")
    private Integer idTool;
    @JsonProperty("value")
    private String value;
    @JsonProperty("name_tool")
    private String nameTool;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("type_fluctuating_situation")
    private Integer typeFluctuatingSituation;
    @JsonProperty("salt")
    private String salt;
    @JsonProperty("salt_parent")
    private String saltParent;
}
