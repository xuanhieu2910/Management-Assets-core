package com.example.csvccdshustbe.repository.fluctuatingSituationAssetRepository;

import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FluctuatingSituationAssetRepositoryCustom {
    Page<FindAllFluctuatingSituationAssetResponses> findAllFluctuatingSituationAssetByIdFluctuatingSituation(Pageable pageable,
                                                                                     FindAllFluctuatingSituationAssetRequest request);
}
