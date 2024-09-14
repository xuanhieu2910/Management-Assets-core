package com.example.csvccdshustbe.dto.original;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetOriginalDto {

    @JsonProperty("blue_print_asset_original")
    private BluePrintOriginalDto bluePrintAssetOriginalDto;
    @JsonProperty("data_details")
    private Object dataDetails;
}
