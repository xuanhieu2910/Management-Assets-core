package com.example.csvccdshustbe.response.fluctuatingSituationAsset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllFluctuatingSituationAssetResponses {


    @JsonProperty("id_fluctuating_situation_asset")
    private Integer idFluctuatingSituationAsset;
    @JsonProperty("id_asset")
    private Integer idAsset;
    @JsonProperty("value")
    private String value;
    @JsonProperty("name_asset")
    private String nameAsset;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("type_fluctuating_situation")
    private Integer typeFluctuatingSituation;
    @JsonProperty("salt")
    private String salt;
}
