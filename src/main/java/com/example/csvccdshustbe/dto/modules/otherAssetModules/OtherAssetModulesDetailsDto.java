package com.example.csvccdshustbe.dto.modules.otherAssetModules;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OtherAssetModulesDetailsDto {

    @JsonProperty("id_other_asset_module")
    private Integer idOtherAssetModule;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("label")
    private String label;
    @JsonProperty("model")
    private String model;
    @JsonProperty("serial")
    private String serial;
    @JsonProperty("publish_date")
    private String publishDate;
    @JsonProperty("id_country_producer")
    private Integer idCountryProducer;
    @JsonProperty("id_user")
    private Integer idUser;
    @JsonProperty("id_type_use")
    private Integer idTypeUse;
    @JsonProperty("name_country_producer")
    private String nameCountryProducer;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("name_type_use")
    private String nameTypeUse;
}
