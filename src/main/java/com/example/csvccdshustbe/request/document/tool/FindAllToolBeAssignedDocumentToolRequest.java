package com.example.csvccdshustbe.request.document.tool;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class FindAllToolBeAssignedDocumentToolRequest extends RequestPageBase {

    private Integer idDepartment;
    private List<Integer> idsDepartment;
}
