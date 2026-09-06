package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllProcessBeAssignedDocumentInventoryRequest extends RequestPageBase {

    private Integer idDepartment;
    private List<Integer> idsDepartment;

}
