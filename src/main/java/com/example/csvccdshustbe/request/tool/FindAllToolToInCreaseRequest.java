package com.example.csvccdshustbe.request.tool;


import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllToolToInCreaseRequest extends RequestPageBase {
    private String nameTool;
    private Integer idToolCategory;
    private Integer idDepartment;
    private Boolean notChildren = true;
    private String salt;
    private Integer statusUse;
    private List<Integer> idsDepartmentOriginal;
}
