package com.example.csvccdshustbe.request.user;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllUserRequest extends RequestPageBase {

    private List<Integer> idsDepartment;
    private String fullName;
    private Integer idDepartment;
    private String nameRole;
}
