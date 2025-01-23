package com.example.csvccdshustbe.service.fluctuatingSituationToolService;

import com.example.csvccdshustbe.entity.FluctuatingSituationTool;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.StatisticFluctuatingSituationTool;

import java.util.List;

public interface FluctuatingSituationToolService {

    List<FluctuatingSituationTool> saveAll(List<FluctuatingSituationTool> fluctuatingSituationToolList);

    StatisticFluctuatingSituationTool getStatisticFluctuatingSituationTool(Integer idFluctuatingSituation);
}
