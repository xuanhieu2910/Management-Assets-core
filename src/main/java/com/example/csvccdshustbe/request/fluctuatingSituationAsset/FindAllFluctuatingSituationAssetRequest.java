package com.example.csvccdshustbe.request.fluctuatingSituationAsset;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class FindAllFluctuatingSituationAssetRequest extends RequestPageBase {

    @NotNull
    private Integer idFluctuatingSituation;
    private String nameAsset;
    private Integer typeFluctuatingSituation;
    private Integer status;
}
