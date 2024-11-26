package com.example.csvccdshustbe.response.reason;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllReasonResponse {
    @JsonProperty("id_reason")
    private Integer idReason;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type_reason")
    private Integer typeReason;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("time_created")
    private String timeCreated;
    @JsonProperty("time_modified")
    private String timeModified;
    @JsonProperty("type_action")
    private String typeAction;
}
