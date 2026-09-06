package com.example.csvccdshustbe.dto.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroundAssetDto {
    private Integer idGroundAsset;
    private String nameGroundAsset;
    private String codeGroundAsset;
    private String salt;
    private String nameDepartment;
}
