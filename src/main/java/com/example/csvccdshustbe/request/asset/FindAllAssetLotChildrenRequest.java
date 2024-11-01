package com.example.csvccdshustbe.request.asset;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllAssetLotChildrenRequest extends RequestPageBase {
    @NotNull
    private String saltAssetParent;
    private String nameAsset;
    private Integer idDepartment;
    private List<Integer> idsDepartmentOriginal;
}
