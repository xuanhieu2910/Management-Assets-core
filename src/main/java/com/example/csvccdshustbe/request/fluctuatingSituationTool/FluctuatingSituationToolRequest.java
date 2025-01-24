package com.example.csvccdshustbe.request.fluctuatingSituationTool;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FluctuatingSituationToolRequest {

    private List<Integer> idsFluctuatingSituationTool;
    private Integer typeCurrent;
}
