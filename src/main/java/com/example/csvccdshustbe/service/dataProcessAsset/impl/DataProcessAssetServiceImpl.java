package com.example.csvccdshustbe.service.dataProcessAsset.impl;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.DataProcessAsset;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.dataProcessAsset.DataProcessAssetRepository;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetResponse;
import com.example.csvccdshustbe.service.dataProcessAsset.DataProcessAssetService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Page<FindAllProcessAssetResponse> findAllDataProcessAsset(FindAllProcessAssetRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setIdsDepartmentOriginal(request);
        Page<FindAllProcessAssetDto> findAllProcessAssetDtos = dataProcessAssetRepository.findAllProcessAssetDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetResponse(findAllProcessAssetDtos.get().collect(Collectors.toList())),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }



    private List<FindAllProcessAssetResponse> convertToFindAllProcessAssetResponse(List<FindAllProcessAssetDto> collect) {
        List<FindAllProcessAssetResponse> responses = new ArrayList<>();
        for (FindAllProcessAssetDto dto : collect) {
            FindAllProcessAssetResponse response = new FindAllProcessAssetResponse();
            response.setCodeDocument(dto.getCodeDocument());
            response.setIdUserCreate(dto.getIdUserCreate());
            response.setCodeUserCreate(dto.getCodeUserCreate());
            response.setNameUserCreate(dto.getNameUserCreate());
            response.setStatus(dto.getStatus());
            response.setTimeIncrease(dto.getTimeIncrease());

            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(
                    DateUtil.formatDatePattern(dto.getTimeCreated(),
                            DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT_HH_MM));
            response.setTimeModified(DateUtil.formatToPattern(
                    DateUtil.formatDatePattern(dto.getTimeModified(),
                            DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT_HH_MM));
            response.setTimeDocument(dto.getTimeDocument());
            responses.add(response);
        }
        return responses;
    }

    private void setIdsDepartmentOriginal(FindAllProcessAssetRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
    }

}
