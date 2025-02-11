package com.example.csvccdshustbe.response.toolInstance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllToolInstanceResponse {
    @JsonProperty("id_tool_instance")
    private Integer idToolInstance;
    @JsonProperty("value")
    private String value;
    @JsonProperty("total_error")
    private long totalError = 0;
}
