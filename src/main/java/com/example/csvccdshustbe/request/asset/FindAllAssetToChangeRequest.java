package com.example.csvccdshustbe.request.asset;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllAssetToChangeRequest extends RequestPageBase {

    private String nameAsset;
    private Integer idAssetCategory;
    private Integer idDepartment;
    private Boolean isSingle = true;
    private List<Integer> idsDepartmentOriginal;

}
