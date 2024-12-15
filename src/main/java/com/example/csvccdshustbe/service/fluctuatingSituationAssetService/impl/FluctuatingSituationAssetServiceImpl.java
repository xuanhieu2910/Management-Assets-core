package com.example.csvccdshustbe.service.fluctuatingSituationAssetService.impl;

import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.repository.fluctuatingSituationAssetRepository.FluctuatingSituationAssetRepository;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetResponses;
import com.example.csvccdshustbe.service.fluctuatingSituationAssetService.FluctuatingSituationAssetService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FluctuatingSituationAssetServiceImpl implements FluctuatingSituationAssetService {

    @Autowired
    FluctuatingSituationAssetRepository fluctuatingSituationAssetRepository;

    @Override
    public List<FluctuatingSituationAsset> saveAllFluctuatingSituationAsset(List<FluctuatingSituationAsset> fluctuatingSituationAssets) {
        return fluctuatingSituationAssetRepository.saveAll(fluctuatingSituationAssets);
    }

    @Override
    public Page<FindAllFluctuatingSituationAssetResponses>
    findAllFluctuatingSituationAsset(FindAllFluctuatingSituationAssetRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        return fluctuatingSituationAssetRepository.findAllFluctuatingSituationAssetByIdFluctuatingSituation(pageable, request);
    }
}
