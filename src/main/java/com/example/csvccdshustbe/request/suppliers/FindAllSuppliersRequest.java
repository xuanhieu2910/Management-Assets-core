package com.example.csvccdshustbe.request.suppliers;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllSuppliersRequest extends RequestPageBase {

    private Integer status;
    private String name;
    private String phoneNumber;
    private String email;
    private String fax;
    private String address;
    private String url;
    private Integer idDepartment;


    private List<Integer> idsDepartment;
}
