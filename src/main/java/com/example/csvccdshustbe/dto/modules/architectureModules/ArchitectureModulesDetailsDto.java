package com.example.csvccdshustbe.dto.modules.architectureModules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchitectureModulesDetailsDto {

    @JsonProperty("id_architecture_module")
    private Integer idArchitectureModule;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("id_instance")
    private Integer idInstance;
    @JsonProperty("length")
    private Double length;
    @JsonProperty("acreage")
    private Double acreage;
    @JsonProperty("volume")
    private Double volume;
    @JsonProperty("publish_date")
    private String publishDate;
    @JsonProperty("id_country_producer")
    private Integer idCountryProducer;
    @JsonProperty("name_instance")
    private String nameInstance;
    @JsonProperty("name_country_producer")
    private String nameCountryProducer;
}
