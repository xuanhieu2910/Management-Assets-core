package com.example.csvccdshustbe.request.fluctuatingSituation;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class FindAllFluctuatingSituationRequest extends RequestPageBase {

    private String codeDocument;
    private Integer statusFluctuatingSituation;
    private Integer idDepartment;
    @NotNull
    private Integer type;
    private List<Integer> idsDepartmentOriginal;
}
