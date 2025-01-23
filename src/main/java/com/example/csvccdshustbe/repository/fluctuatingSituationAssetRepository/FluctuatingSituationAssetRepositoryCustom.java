package com.example.csvccdshustbe.repository.fluctuatingSituationAssetRepository;

import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetResponses;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.StatisticFluctuatingSituationAsset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FluctuatingSituationAssetRepositoryCustom {
    Page<FindAllFluctuatingSituationAssetResponses> findAllFluctuatingSituationAssetByIdFluctuatingSituation(Pageable pageable,
                                                                                     FindAllFluctuatingSituationAssetRequest request);
    List<FluctuatingSituationAsset> findFluctuatingSituationAssetByIds(List<Integer> idsFluctuatingSituationAsset);

    StatisticFluctuatingSituationAsset getStatisticFluctuatingSituationAsset(Integer idFluctuatingSituation);
}
