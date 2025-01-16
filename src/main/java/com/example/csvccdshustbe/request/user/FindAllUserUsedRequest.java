package com.example.csvccdshustbe.request.user;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllUserUsedRequest extends RequestPageBase {
    private String fullName;
    private Integer idDepartment;
    private String nameRole;
    private List<Integer> idsDepartment;
}
