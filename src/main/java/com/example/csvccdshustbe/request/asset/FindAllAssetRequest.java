package com.example.csvccdshustbe.request.asset;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllAssetRequest  extends RequestPageBase {

    private String nameAsset;
    private Integer idAssetCategory;
    private Integer idDepartment;

}
