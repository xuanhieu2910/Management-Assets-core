package com.example.csvccdshustbe.request.toolProcess;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllToolProcessRequest extends RequestPageBase {


    @NotNull
    private String codeDocument;
    private String nameTool;
    private Integer idToolCategory;
    private Integer idDepartment;
    private String salt;
    private List<Integer> idsDepartmentOriginal;
}
