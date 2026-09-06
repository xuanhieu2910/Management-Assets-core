package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllProcessAssetRevaluationRequest extends RequestPageBase {
    private String codeDocument;
    private Integer status;
    private String nameUserCreate;
    private String timeCreated;
    private String timeDocument;
    private String timeRevaluation;
    private Integer idDepartment;
    private String nameDepartment;

    private List<Integer> idsDepartmentOriginal;
}
