package com.example.csvccdshustbe.dto.originalOfFormation;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssetOriginalOfFormDto {

    @JsonProperty("id_original_of_formation")
    private Integer idOriginalOfFormation;
    @JsonProperty("name")
    private String nameOriginalOfFormation;
    @JsonProperty("value")
    private String value;
}
