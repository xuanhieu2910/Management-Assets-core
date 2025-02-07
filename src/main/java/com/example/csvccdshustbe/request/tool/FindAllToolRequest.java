package com.example.csvccdshustbe.request.tool;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class FindAllToolRequest extends RequestPageBase {

    private String salt;
    private String codeTool;
    private String nameTool;
    private Integer idToolCategory;
    private Integer idDepartment;
    private Integer isIncrease;
    private Integer isDecrease;
    private Integer statusUse;
    private Integer quantity;
    private Integer typeSearch;
    private List<Integer> idsDepartmentOriginal;

}
