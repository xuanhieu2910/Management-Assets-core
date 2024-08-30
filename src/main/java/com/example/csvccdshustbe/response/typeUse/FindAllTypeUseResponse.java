package com.example.csvccdshustbe.response.typeUse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllTypeUseResponse {
    @JsonProperty("id_type_use")
    private Integer idTypeUse;
    @JsonProperty("name")
    private String name;
}
