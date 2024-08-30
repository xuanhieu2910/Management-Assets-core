package com.example.csvccdshustbe.request.department;


import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDepartmentSRequest extends RequestPageBase {

    private Integer status;
    private String code;
    private String shortName;
}
