package com.example.csvccdshustbe.request.department;


import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDepartmentRequest extends RequestPageBase {

    private Integer status;
    private String code;
    private String shortName;
    private Integer idDepartment;
}
