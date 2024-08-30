package com.example.csvccdshustbe.request.department;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateDepartmentRequest {

    private Integer parentId;
    @NonNull
    private String name;
    private String code;
    private String shortName;
    private String description;
    @NonNull
    private Integer status;
}
