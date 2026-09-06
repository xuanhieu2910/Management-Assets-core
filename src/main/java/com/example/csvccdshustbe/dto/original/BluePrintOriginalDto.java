package com.example.csvccdshustbe.dto.original;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BluePrintOriginalDto {


    @JsonProperty("type_original")
    private String typeOriginal;
    @JsonProperty("id_original")
    private Integer idOriginal;
    @JsonProperty("name_original")
    private String nameOriginal;
    @JsonProperty("id_instance")
    private Integer idInstance;
}
