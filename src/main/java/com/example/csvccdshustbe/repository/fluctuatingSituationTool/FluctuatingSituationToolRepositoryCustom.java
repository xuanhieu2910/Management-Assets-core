package com.example.csvccdshustbe.repository.fluctuatingSituationTool;

import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.entity.FluctuatingSituationTool;
import com.example.csvccdshustbe.request.fluctuatingSituationTool.FindAllFluctuatingSituationToolRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.FindAllFluctuatingSituationToolResponses;
import com.example.csvccdshustbe.response.fluctuatingSituationTool.StatisticFluctuatingSituationTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FluctuatingSituationToolRepositoryCustom {
    StatisticFluctuatingSituationTool getStatisticFluctuatingSituationTool(Integer idFluctuatingSituation);
    Page<FindAllFluctuatingSituationToolResponses> findAllFluctuatingSituationTool(FindAllFluctuatingSituationToolRequest request, Pageable pageable);
    List<FluctuatingSituationTool> findFluctuatingSituationToolByIds(List<Integer> idsFluctuatingSituationTool);
}
