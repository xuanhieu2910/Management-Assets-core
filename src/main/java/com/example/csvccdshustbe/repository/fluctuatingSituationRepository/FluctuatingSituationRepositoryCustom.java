package com.example.csvccdshustbe.repository.fluctuatingSituationRepository;

import com.example.csvccdshustbe.request.fluctuatingSituation.FindAllFluctuatingSituationRequest;
import com.example.csvccdshustbe.response.fluctuatingSituation.FindAllFluctuationSituationResponse;
import com.example.csvccdshustbe.response.fluctuatingSituation.StatisticFluctuatingSituation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FluctuatingSituationRepositoryCustom {
    Page<FindAllFluctuationSituationResponse> findAllFluctuationSituationResponse(Pageable pageable,
                                                                                  FindAllFluctuatingSituationRequest request);
    void calculatorStatusFluctuatingSituationById(Integer idFluctuatingSituation);

    StatisticFluctuatingSituation getStatisticFluctuatingSituationNotFinish();
}
