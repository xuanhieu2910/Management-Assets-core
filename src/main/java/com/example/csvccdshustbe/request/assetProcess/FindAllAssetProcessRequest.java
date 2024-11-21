package com.example.csvccdshustbe.request.assetProcess;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllAssetProcessRequest extends RequestPageBase {

    @NotNull
    private String codeDocument;
    private String nameAsset;
    private Integer idAssetCategory;
    private Integer idDepartment;
    private List<Integer> idsDepartmentOriginal;

}
