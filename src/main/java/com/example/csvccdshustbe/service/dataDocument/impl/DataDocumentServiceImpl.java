package com.example.csvccdshustbe.service.dataDocument.impl;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetIncreaseDto;
import com.example.csvccdshustbe.dto.process.FindAllProcessAssetInventoryDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.DataDocument;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.dataDocument.DataDocumentRepository;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetIncreaseRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetInventoryRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetIncreaseResponse;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetInventoryResponse;
import com.example.csvccdshustbe.service.dataDocument.DataDocumentService;
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

@Service
public class DataDocumentServiceImpl implements DataDocumentService {

    @Autowired
    DataDocumentRepository dataDocumentRepository;


    @Override
    public List<DataDocument> findDataProcessIncreaseAssetByIdsAsset(List<Integer> idsAsset) {
        return dataDocumentRepository.findDataProcessIncreaseAssetByIdsAsset(idsAsset);
    }

    @Override
    public void createNewDataProcessAssetIncrease(CreateIncreaseAssetRequest request, Document document)
            throws ValidateFiledException {
        validateDataProcessAsset(request.getIdsAsset());
        saveAllDataProcessAsset(createConstructDataProcessAsset(document, request));
    }


    private List<DataDocument> saveAllDataProcessAsset(List<DataDocument> constructDataProcessAsset) {
        return dataDocumentRepository.saveAll(constructDataProcessAsset);
    }

    private List<DataDocument> createConstructDataProcessAsset(Document document,
                                                             CreateIncreaseAssetRequest request) {
        List<DataDocument> dataProcessAssets = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (Integer idAsset: request.getIdsAsset()){
            DataDocument dataProcessAsset = new DataDocument();
            dataProcessAsset.setIdAsset(idAsset);
            dataProcessAsset.setIdDocument(document.getIdDocument());
            dataProcessAsset.setTimeCreated(timeCurrent);
            dataProcessAsset.setTimeModified(timeCurrent);
            dataProcessAsset.setStatus(Constants.STATUS_DATA_DOCUMENT_ACTIVE);
            dataProcessAssets.add(dataProcessAsset);
        }
        return dataProcessAssets;
    }

    private void validateDataProcessAsset(List<Integer> idsAsset) throws ValidateFiledException {
        List<DataDocument> dataProcessAssets = findDataProcessIncreaseAssetByIdsAsset(idsAsset);
        if (!CollectionUtils.isEmpty(dataProcessAssets)){
            throw new ValidateFiledException("Exist data process asset!");
        }
    }

    @Override
    public Page<FindAllProcessAssetIncreaseResponse> findAllDataProcessAssetIncrease(FindAllProcessAssetIncreaseRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllProcessAssetIncreaseDto> findAllProcessAssetDtos =
                dataDocumentRepository.findAllProcessAssetIncreaseDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetIncreaseResponse(findAllProcessAssetDtos.stream().toList()),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }

    @Override
    public Page<FindAllProcessAssetInventoryResponse> findAllDataProcessAssetInventory(FindAllProcessAssetInventoryRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllProcessAssetInventoryDto> findAllProcessAssetDtos =
                dataDocumentRepository.findAllProcessAssetInventoryDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetInventoryResponse(findAllProcessAssetDtos.stream().toList()),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }

    private List<FindAllProcessAssetIncreaseResponse>
    convertToFindAllProcessAssetIncreaseResponse(List<FindAllProcessAssetIncreaseDto> collect) {
        List<FindAllProcessAssetIncreaseResponse> responses = new ArrayList<>();
        for (FindAllProcessAssetIncreaseDto dto : collect) {
            FindAllProcessAssetIncreaseResponse response = new FindAllProcessAssetIncreaseResponse();
            response.setCodeDocument(dto.getCodeDocument());
            response.setIdUserCreate(dto.getIdUserCreate());
            response.setCodeUserCreate(dto.getCodeUserCreate());
            response.setNameUserCreate(dto.getNameUserCreate());
            response.setStatus(dto.getStatus());
            response.setTimeIncrease(dto.getTimeIncrease());

            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setTimeDocument(dto.getTimeDocument());
            responses.add(response);
        }
        return responses;
    }


    private List<FindAllProcessAssetInventoryResponse>
    convertToFindAllProcessAssetInventoryResponse(List<FindAllProcessAssetInventoryDto> collect) {
        List<FindAllProcessAssetInventoryResponse> responses = new ArrayList<>();
        for (FindAllProcessAssetInventoryDto dto : collect) {
            FindAllProcessAssetInventoryResponse response = new FindAllProcessAssetInventoryResponse();
            response.setCodeDocument(dto.getCodeDocument());
            response.setIdUserCreate(dto.getIdUserCreate());
            response.setCodeUserCreate(dto.getCodeUserCreate());
            response.setNameUserCreate(dto.getNameUserCreate());
            response.setStatus(dto.getStatus());
            response.setTimeInventory(dto.getTimeInventory());

            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setTimeDocument(dto.getTimeDocument());
            responses.add(response);
        }
        return responses;
    }

}
