package com.example.csvccdshustbe.dto.modules.treeAndAnimalModules;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeAndAnimalModulesDetailsDto {

    @JsonProperty("id_animal_tree_module")
    private Integer idAnimalTreeModule;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("publish_date")
    private String publishDate;
    @JsonProperty("id_type_use")
    private Integer idTypeUse;
    @JsonProperty("id_country_producer")
    private Integer idCountryProducer;
    @JsonProperty("name_country_producer")
    private String nameCountryProducer;
    @JsonProperty("name_type_use")
    private String nameTypeUse;
}
