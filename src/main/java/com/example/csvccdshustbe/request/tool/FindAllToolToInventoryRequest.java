package com.example.csvccdshustbe.request.tool;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllToolToInventoryRequest  extends RequestPageBase {

    private String codeTool;
    private String nameTool;
    private Integer idToolCategory;
    private String nameToolCategory;
    private Integer idDepartment;
    private String nameDepartment;
    private Integer idLocation;
    private String nameLocation;
    private String userName;
    private Integer statusUse;

    private List<Integer> idsDepartment;
}
