package com.example.csvccdshustbe.request.department;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDepartmentVisibleRequest extends RequestPageBase {

    private List<Integer> idDepartmentOriginal;
}
