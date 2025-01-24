package com.example.csvccdshustbe.request.fluctuatingSituationTool;

import com.example.csvccdshustbe.request.RequestPageBase;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAllFluctuatingSituationToolRequest  extends RequestPageBase {

    @NotNull
    private Integer idFluctuatingSituation;
    private String nameTool;
    private Integer typeFluctuatingSituation;
    private Integer status;
}
