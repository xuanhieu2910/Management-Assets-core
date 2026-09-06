package com.example.csvccdshustbe.request.process;

import com.example.csvccdshustbe.request.RequestPageBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllProcessBeAssignedRequest  extends RequestPageBase {

    private String codeTypeProcess;
    private String codeDocument;
    private String nameUserCreate;
    private String timeCreated;
    private String timeDocument;
    private String timeIncrease;
    private String description;

}
