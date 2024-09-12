package com.example.csvccdshustbe.service.original.methodBuyAsset.impl;

import com.example.csvccdshustbe.entity.MethodBuyAsset;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.methodBuyAsset.MethodBuyAssetRepository;
import com.example.csvccdshustbe.request.methodBuyAsset.CreateMethodBuyAssetRequest;
import com.example.csvccdshustbe.request.methodBuyAsset.FindAllMethodBuyAssetPickedRequest;
import com.example.csvccdshustbe.request.methodBuyAsset.UpdateMethodBuyAssetRequest;
import com.example.csvccdshustbe.response.methodBuyAsset.FindAllMethodBuyAssetResponse;
import com.example.csvccdshustbe.service.original.methodBuyAsset.MethodBuyAssetService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MethodBuyAssetServiceImpl implements MethodBuyAssetService {

    @Autowired
    MethodBuyAssetRepository methodBuyAssetRepository;

    @Override
    public Page<FindAllMethodBuyAssetResponse> findAllActiveMethodBuyAssetResponse(FindAllMethodBuyAssetPickedRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<MethodBuyAsset> methodBuyAssets = methodBuyAssetRepository.findAllActiveMethodBuyAsset(request, pageable);
        return new PageImpl<>(convertToFindAllMethodBuyAssetResponse(methodBuyAssets.get().collect(Collectors.toList())),
                pageable, methodBuyAssets.getTotalElements());
    }

    private List<FindAllMethodBuyAssetResponse> convertToFindAllMethodBuyAssetResponse(List<MethodBuyAsset> collect) {
        List<FindAllMethodBuyAssetResponse> responses = new ArrayList<>();
        for (MethodBuyAsset methodBuyAsset : collect){
            FindAllMethodBuyAssetResponse response = new FindAllMethodBuyAssetResponse();
            response.setIdMethodBuyAsset(methodBuyAsset.getIdMethodBuyAsset());
            response.setName(methodBuyAsset.getTitle());
            responses.add(response);
        }
        return responses;
    }
    @Override
    public void createMethodBuyAssetService(CreateMethodBuyAssetRequest request) throws ValidateFiledException {
        validateDataCreateMethodBuyAssetService(request);
        methodBuyAssetRepository.save(contructMethodBuyAsset(request));
    }
    private MethodBuyAsset contructMethodBuyAsset(CreateMethodBuyAssetRequest request){
        MethodBuyAsset methodBuyAsset =new MethodBuyAsset();
        methodBuyAsset.setTitle(request.getTitle().trim());
        methodBuyAsset.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        methodBuyAsset.setTimeCreated(timeCurrent);
        methodBuyAsset.setTimeModified(timeCurrent);
        return methodBuyAsset;
    }

    private void validateDataCreateMethodBuyAssetService(CreateMethodBuyAssetRequest request) throws ValidateFiledException{
        if (StringUtils.isBlank(request.getTitle())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<MethodBuyAsset>methodBuyAssetOptional=methodBuyAssetRepository.findMethodBuyAssetByTitle(request.getTitle());
        if (methodBuyAssetOptional.isPresent()) {
            throw new ValidateFiledException("Exits Method buy asset by Title!");
        }

    }
    @Override
    public void updateMethodBuyAssetService(UpdateMethodBuyAssetRequest request) throws ValidateFiledException{
        MethodBuyAsset methodBuyAsset = validateUpdateMethodBuyAsset(request);
        methodBuyAssetRepository.save(editMethodBuyAsset(methodBuyAsset,request));

    }

    private MethodBuyAsset editMethodBuyAsset(MethodBuyAsset methodBuyAsset,UpdateMethodBuyAssetRequest request){
        methodBuyAsset.setTitle(request.getTitle());
        methodBuyAsset.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        methodBuyAsset.setTimeModified(timeModified);
        return methodBuyAsset;
    }
    private MethodBuyAsset validateUpdateMethodBuyAsset(UpdateMethodBuyAssetRequest request) throws ValidateFiledException{
    Optional<MethodBuyAsset> methodBuyAssetOptional=methodBuyAssetRepository.findMethodBuyAssetById(request.getIdMethodBuyAsset());
        if (!methodBuyAssetOptional.isPresent()) {
            throw new NotFoundException("Don't exits Medicine Type by id!");
        }
        if (StringUtils.isBlank(request.getTitle())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return methodBuyAssetOptional.get();

    }
    @Override
    public void deleteMethodBuyAssetService(Integer idMethodBuyAsset) {
        Optional<MethodBuyAsset> methodBuyAssetOptional=methodBuyAssetRepository.findMethodBuyAssetById(idMethodBuyAsset);
        if (!methodBuyAssetOptional.isPresent()) {
            throw new NotFoundException("Don't exits Method buy asset by id!");
        }
        methodBuyAssetRepository.delete(methodBuyAssetOptional.get());
    }
}
