package com.example.csvccdshustbe.request.assetCategories;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindAllDocumentAssetCategoriesRequest extends RequestPageBase {

    private List<Integer> idsDepartmentOriginal;

}
