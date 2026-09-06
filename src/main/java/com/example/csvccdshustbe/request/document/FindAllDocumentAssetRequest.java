package com.example.csvccdshustbe.request.document;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class FindAllDocumentAssetRequest extends RequestPageBase {

    private Integer statusTypeProcess;
    private String codeAsset;
    private Integer idDocument;
    private String codeDocument;
    private Integer status;
    private Integer idUserCreate;
    private String codeUserCreate;
    private String nameUserCreate;
    private String timeCreated;
    private String timeDocument;
    private String description;
    private String salt;
    private List<Integer> idsDepartmentOriginal;
}
