package com.example.csvccdshustbe.request.toolCategories;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllToolCategoriesRequest extends RequestPageBase {
    private List<Integer> idsDepartmentOriginal;
    private Integer status;
}
