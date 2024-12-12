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
    private Integer idDepartment;
    private Integer idLocation;
    private Integer isIncrease;
    private Integer isDecrease;
    private Boolean isSingle;
    private List<Integer> idsDepartmentOriginal;
}
