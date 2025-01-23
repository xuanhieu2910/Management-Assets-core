package com.example.csvccdshustbe.service.fluctuatingSituationService;

import com.example.csvccdshustbe.entity.FluctuatingSituation;
import com.example.csvccdshustbe.request.fluctuatingSituation.FindAllFluctuatingSituationRequest;
import com.example.csvccdshustbe.response.fluctuatingSituation.FindAllFluctuationSituationResponse;
import com.example.csvccdshustbe.response.fluctuatingSituation.StatisticFluctuatingSituation;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.StatisticFluctuatingSituationAsset;
import org.springframework.data.domain.Page;

public interface FluctuatingSituationService {

    FluctuatingSituation saveFluctuatingSituation(FluctuatingSituation fluctuatingSituation);
    Page<FindAllFluctuationSituationResponse> findAllFluctuatingSituation(FindAllFluctuatingSituationRequest request);
    void calculatorStatusFluctuatingSituationById(Integer idFluctuatingSituation);
    StatisticFluctuatingSituation getStatisticFluctuatingSituation(Integer typeFluctuatingSituation);
}
