package com.example.csvccdshustbe.service.dataProcessAsset.impl;

import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.dataProcessAsset.DataProcessAssetRepository;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.service.dataProcessAsset.DataProcessAssetService;
import com.example.csvccdshustbe.service.document.DocumentService;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.service.request.RequestService;
import com.example.csvccdshustbe.service.state.StateService;
import com.example.csvccdshustbe.service.typeProcessService.TypeProcessService;
import com.example.csvccdshustbe.service.typeState.TypeStateService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DataProcessAssetServiceImpl implements DataProcessAssetService {

    @Autowired
    DataProcessAssetRepository dataProcessAssetRepository;


    @Override
    public List<DataProcessAsset> findDataProcessIncreaseAssetByIdsAsset(List<Integer> idsAsset) {
        return dataProcessAssetRepository.findDataProcessIncreaseAssetByIdsAsset(idsAsset);
    }

    @Override
    public void createNewDataProcessAsset(CreateIncreaseAssetRequest request, Document document, Process process)
            throws ValidateFiledException {
        validateDataProcessAsset(request.getIdsAsset());
        saveAllDataProcessAsset(createConstructDataProcessAsset(process, document, request));
    }


    private List<DataProcessAsset> saveAllDataProcessAsset(List<DataProcessAsset> constructDataProcessAsset) {
        return dataProcessAssetRepository.saveAll(constructDataProcessAsset);
    }

    private List<DataProcessAsset> createConstructDataProcessAsset(Process process, Document document,
                                                             CreateIncreaseAssetRequest request) {
        List<DataProcessAsset> dataProcessAssets = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (Integer idAsset: request.getIdsAsset()){
            DataProcessAsset dataProcessAsset = new DataProcessAsset();
            dataProcessAsset.setIdAsset(idAsset);
            dataProcessAsset.setIdDocument(document.getIdDocument());
            dataProcessAsset.setIdProcess(process.getIdProcess());
            dataProcessAsset.setTimeCreated(timeCurrent);
            dataProcessAsset.setTimeModified(timeCurrent);
            dataProcessAsset.setStatus(Constants.STATUS_PROCESS_ASSET_ACTIVE);
            dataProcessAssets.add(dataProcessAsset);
        }
        return dataProcessAssets;
    }

    private void validateDataProcessAsset(List<Integer> idsAsset) throws ValidateFiledException {
        List<DataProcessAsset> dataProcessAssets = findDataProcessIncreaseAssetByIdsAsset(idsAsset);
        if (!CollectionUtils.isEmpty(dataProcessAssets)){
            throw new ValidateFiledException("Exist data process asset!");
        }
    }
}
