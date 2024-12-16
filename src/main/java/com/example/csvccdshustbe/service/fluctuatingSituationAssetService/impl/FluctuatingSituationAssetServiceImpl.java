package com.example.csvccdshustbe.service.fluctuatingSituationAssetService.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.fluctuatingSituationAssetRepository.FluctuatingSituationAssetRepository;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.response.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetResponses;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.service.fluctuatingSituationAssetService.FluctuatingSituationAssetService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.JSONObjectUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;

@Service
public class FluctuatingSituationAssetServiceImpl implements FluctuatingSituationAssetService {

    private final static ObjectMapper objectMapper = new ObjectMapper();

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
        FluctuatingSituationAsset fluctuatingSituationAsset = findFluctuatingSituationAssetById(updateAssetRequest.getIdFluctuatingSituationAsset());
//        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        fluctuatingSituationAsset.setType(Constants.TYPE_FLUCTUATING_SITUATION_ASSET_INCREASE);
//        fluctuatingSituationAsset.setStatus(Constants.STATUS_FLUCTUATING_SITUATION_ASSET_NOT_FINISH);
//        fluctuatingSituationAsset.setTimeModified(String.valueOf(new Date().getTime()));
//        fluctuatingSituationAsset.setIdUserModified(csvcUser.getIdUser());
//        fluctuatingSituationAssetRepository.save(fluctuatingSituationAsset);
    }

    @Override
    public FluctuatingSituationAsset findFluctuatingSituationAssetById(Integer idFluctuatingSituation) {
        Optional<FluctuatingSituationAsset> fluctuatingSituationAsset =
                fluctuatingSituationAssetRepository.findFluctuatingSituationAssetById(idFluctuatingSituation);
        if (fluctuatingSituationAsset.isEmpty()){
            throw new NotFoundException("Don't exits fluctuating situation asset by id!");
        }
        return fluctuatingSituationAsset.get();
    }
}
