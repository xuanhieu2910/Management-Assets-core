package com.example.csvccdshustbe.request.document.tool;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDocumentToolRequest extends RequestPageBase {

    private String codeDocument;
    @NotNull
    private String codeTypeProcess;
    private Integer status;
    private String nameUserCreate;
    private String timeDocument;
    private String timeIncrease;
    private Integer idDepartment;
    private List<Integer> idsDepartmentOriginal;


}
