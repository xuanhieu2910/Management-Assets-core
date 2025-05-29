package com.example.csvccdshustbe.service.fluctuatingSituationAssetService.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.entity.FluctuatingSituationTool;
import com.example.csvccdshustbe.repository.fluctuatingSituationAssetRepository.FluctuatingSituationAssetRepository;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetResponses;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.StatisticFluctuatingSituationAsset;
import com.example.csvccdshustbe.service.fluctuatingSituationAssetService.FluctuatingSituationAssetService;
import com.example.csvccdshustbe.service.fluctuatingSituationService.FluctuatingSituationService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;

@Service
public class FluctuatingSituationAssetServiceImpl implements FluctuatingSituationAssetService {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Lazy
    @Autowired
    FluctuatingSituationService fluctuatingSituationService;

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

    @Transactional
    @Override
    public void updateDeclareAssetFluctuatingSituation(FluctuatingSituationAssetRequest updateAssetRequest) {
        List<FluctuatingSituationAsset> fluctuatingSituationAssets =
                findFluctuatingSituationAssetByIds(updateAssetRequest.getIdsFluctuatingSituationAsset());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (FluctuatingSituationAsset fluctuatingSituationAsset : fluctuatingSituationAssets) {
            fluctuatingSituationAsset.setTimeModified(String.valueOf(new Date().getTime()));
            fluctuatingSituationAsset.setIdUserModified(csvcUser.getIdUser());
            if (updateAssetRequest.getTypeCurrent().equals(Constants.TYPE_FLUCTUATING_SITUATION_DECLARE)) {
                fluctuatingSituationAsset.setType(Constants.TYPE_FLUCTUATING_SITUATION_INCREASE);
                fluctuatingSituationAsset.setStatus(Constants.STATUS_FLUCTUATING_SITUATION_ASSET_NOT_FINISH);
            } else {
                fluctuatingSituationAsset.setStatus(Constants.STATUS_FLUCTUATING_SITUATION_ASSET_FINISH);
            }
        }
        fluctuatingSituationAssetRepository.saveAll(fluctuatingSituationAssets);
        updateStatusFluctuatingSituation(fluctuatingSituationAssets.get(0).getIdFluctuatingSituation());
    }

    private void updateStatusFluctuatingSituation(Integer idFluctuatingSituation) {
        fluctuatingSituationService.calculatorStatusFluctuatingSituationById(idFluctuatingSituation);
    }

    @Override
    public List<FluctuatingSituationAsset> findFluctuatingSituationAssetByIds(List<Integer> idsFluctuatingSituation) {
        List<FluctuatingSituationAsset> fluctuatingSituationAssets =
                fluctuatingSituationAssetRepository.findFluctuatingSituationAssetByIds(idsFluctuatingSituation);
        if (CollectionUtils.isEmpty(fluctuatingSituationAssets)){
            throw new NotFoundException("Don't exits fluctuating situation asset by id!");
        }
        return fluctuatingSituationAssets;
    }

    @Override
    public StatisticFluctuatingSituationAsset getStatisticFluctuatingSituationAsset(Integer idFluctuatingSituation) {
        return fluctuatingSituationAssetRepository.getStatisticFluctuatingSituationAsset(idFluctuatingSituation);
    }

    @Override
    public void deleteListFluctuatingSituationAsset(List<FluctuatingSituationAsset> fluctuatingSituationAssets) {
        fluctuatingSituationAssetRepository.deleteAll(fluctuatingSituationAssets);
    }

    @Override
    public List<FluctuatingSituationAsset> findFluctuatingSituationAssetByIdFlu(Integer idFluctuatingSituation) {
        List<FluctuatingSituationAsset> fluctuatingSituationAssets =
                fluctuatingSituationAssetRepository.findFluctuatingSituationAssetByIdFlu(idFluctuatingSituation);
        if (CollectionUtils.isEmpty(fluctuatingSituationAssets)){
            throw new NotFoundException("Don't exits fluctuating situation tool by id!");
        }
        return fluctuatingSituationAssets;
    }
}
