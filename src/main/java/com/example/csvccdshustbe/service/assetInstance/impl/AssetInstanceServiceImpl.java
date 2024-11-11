package com.example.csvccdshustbe.service.assetInstance.impl;

import com.example.csvccdshustbe.entity.AssetInstance;
import com.example.csvccdshustbe.repository.assetInstance.AssetInstanceRepository;
import com.example.csvccdshustbe.request.assetInstance.AssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.DeleteAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.FindAllAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.UpdateAssetInstanceRequest;
import com.example.csvccdshustbe.response.assetInstance.FindAllAssetInstanceResponse;
import com.example.csvccdshustbe.service.assetInstance.AssetInstanceService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetInstanceServiceImpl implements AssetInstanceService {

    @Autowired
    AssetInstanceRepository assetInstanceRepository;

    @Override
    public Page<FindAllAssetInstanceResponse> findAllAssetInstance(FindAllAssetInstanceRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<AssetInstance> assetInstances = assetInstanceRepository.findAllAssetInstance(pageable);
        return new PageImpl<>(convertToAssetInstance(assetInstances.get().collect(Collectors.toList())), pageable, assetInstances.getTotalElements());
    }

    @Override
    public void updateAssetInstance(UpdateAssetInstanceRequest request) {
        List<Integer> idsAssetInstance = new ArrayList<>();
        List<AssetInstanceRequest> assetInstanceRequests = request.getAssetInstances();
        assetInstanceRequests.forEach(x->idsAssetInstance.add(x.getIdAssetInstance()));
        List<AssetInstance> assetInstances = assetInstanceRepository.findAllAssetInstanceByIds(idsAssetInstance);
        if (CollectionUtils.isEmpty(assetInstances) || assetInstances.size() != idsAssetInstance.size()){
            throw new NotFoundException("Don't exits asset instance by ids!");
        }
        for (AssetInstanceRequest assetInstanceRequest : assetInstanceRequests) {
            assetInstances.stream().filter(x->x.getIdAssetInstance()
                    .equals(assetInstanceRequest.getIdAssetInstance())).findFirst().get().setValue(assetInstanceRequest.getValue());
        }
        assetInstanceRepository.saveAll(assetInstances);
    }

    @Override
    public void deleteAssetInstance(DeleteAssetInstanceRequest request) {
        List<Integer> idsAssetInstance = new ArrayList<>();
        List<AssetInstanceRequest> assetInstanceRequests = request.getAssetInstances();
        assetInstanceRequests.forEach(x->idsAssetInstance.add(x.getIdAssetInstance()));
        List<AssetInstance> assetInstances = assetInstanceRepository.findAllAssetInstanceByIds(idsAssetInstance);
        if (CollectionUtils.isEmpty(assetInstances) || assetInstances.size() != idsAssetInstance.size()){
            throw new NotFoundException("Don't exits asset instance by ids!");
        }
        assetInstanceRepository.deleteAll(assetInstances);
    }

    private List<FindAllAssetInstanceResponse> convertToAssetInstance(List<AssetInstance> assetInstances) {
        List<FindAllAssetInstanceResponse> responses = new ArrayList<>();
        for (AssetInstance instance : assetInstances){
            FindAllAssetInstanceResponse response = new FindAllAssetInstanceResponse();
            response.setIdAssetInstance(instance.getIdAssetInstance());
            response.setValue(instance.getValue());
            responses.add(response);
        }
        return responses;
    }
}
