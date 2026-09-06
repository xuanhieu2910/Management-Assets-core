package com.example.csvccdshustbe.dto.declare;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssetDeclareDto {

    @JsonProperty("blue_print_asset_declare")
    private BluePrintDeclareDto bluePrintDeclare;
    @JsonProperty("data_details")
    private Object dataDetail;
}
