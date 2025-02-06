package com.example.csvccdshustbe.request.asset;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FinaAllAssetToIncreaseRequest extends RequestPageBase {
    private String nameAsset;
    private Integer idAssetCategory;
    private Integer idDepartment;
    private Integer typeSearch;
    private String salt;
    private Integer statusUse;
    private List<Integer> idsDepartmentOriginal;
}
