package com.example.csvccdshustbe.service.fluctuatingSituationAssetService;

import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetResponses;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FluctuatingSituationAssetService {

    List<FluctuatingSituationAsset> saveAllFluctuatingSituationAsset(List<FluctuatingSituationAsset> fluctuatingSituationAssets);

    Page<FindAllFluctuatingSituationAssetResponses> findAllFluctuatingSituationAsset(FindAllFluctuatingSituationAssetRequest request);
}
