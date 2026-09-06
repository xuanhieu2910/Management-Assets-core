package com.example.csvccdshustbe.service.fluctuatingSituationToolService;

import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.entity.FluctuatingSituationTool;

import com.example.csvccdshustbe.request.fluctuatingSituationTool.FindAllFluctuatingSituationToolRequest;
import com.example.csvccdshustbe.request.fluctuatingSituationTool.FluctuatingSituationToolRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.FindAllFluctuatingSituationToolResponses;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.StatisticFluctuatingSituationTool;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FluctuatingSituationToolService {

    List<FluctuatingSituationTool> saveAll(List<FluctuatingSituationTool> fluctuatingSituationToolList);

    void updateStatusToolFluctuatingSituation(FluctuatingSituationToolRequest request);

    StatisticFluctuatingSituationTool getStatisticFluctuatingSituationTool(Integer idFluctuatingSituation);

    Page<FindAllFluctuatingSituationToolResponses>
    findAllFluctuatingSituationTool(FindAllFluctuatingSituationToolRequest request);

    List<FluctuatingSituationTool> findFluctuatingSituationToolByIds(List<Integer> idsFluctuatingSituation);

    List<FluctuatingSituationTool> findFluctuatingSituationToolByIdFlu(Integer idsFluctuatingSituation);

    void deleteListFluctuatingSituationTool(List<FluctuatingSituationTool> fluctuatingSituationTools);
}