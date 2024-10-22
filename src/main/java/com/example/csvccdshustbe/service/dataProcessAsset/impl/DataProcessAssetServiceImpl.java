package com.example.csvccdshustbe.service.dataProcessAsset.impl;

import com.example.csvccdshustbe.entity.DataProcessAsset;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.dataProcessAsset.DataProcessAssetRepository;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.service.dataProcessAsset.DataProcessAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public void createNewDataProcessAsset(CreateIncreaseAssetRequest request) throws ValidateFiledException {
        validateDataProcessAsset(request.getIdsAsset());
    }

    private void validateDataProcessAsset(List<Integer> idsAsset) throws ValidateFiledException {
        List<DataProcessAsset> dataProcessAssets = findDataProcessIncreaseAssetByIdsAsset(idsAsset);
        if (!CollectionUtils.isEmpty(dataProcessAssets)){
            throw new ValidateFiledException("Exist data process asset!");
        }
    }
}
