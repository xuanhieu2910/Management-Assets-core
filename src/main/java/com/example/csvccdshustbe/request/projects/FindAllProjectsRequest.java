package com.example.csvccdshustbe.request.projects;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllProjectsRequest extends RequestPageBase {

    private Integer status;
    private List<Integer> idsDepartmentOriginal;
}
