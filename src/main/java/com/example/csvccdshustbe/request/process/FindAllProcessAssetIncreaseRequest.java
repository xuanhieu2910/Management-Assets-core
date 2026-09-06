package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class FindAllProcessAssetIncreaseRequest extends RequestPageBase {

    private String codeDocument;
    private Integer status;
    private String nameUserCreate;
    private String timeCreated;
    private String timeDocument;
    private String timeIncrease;
    private Integer idDepartment;
    private String nameDepartment;

    private List<Integer> idsDepartmentOriginal;

}
