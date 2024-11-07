package com.example.csvccdshustbe.service.dataDocument.impl;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.DataDocument;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.dataDocument.DataDocumentRepository;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.request.process.CreateInventoryAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
import com.example.csvccdshustbe.request.process.asset.AssetDetailInventoryRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetResponse;
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
import java.util.Arrays;
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
    public Page<FindAllProcessAssetResponse> findAllDataProcessAsset(FindAllProcessAssetRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setIdsDepartmentOriginal(request);
        Page<FindAllProcessAssetDto> findAllProcessAssetDtos = dataDocumentRepository.findAllProcessAssetDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetResponse(findAllProcessAssetDtos.stream().toList()),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }

    @Override
    public List<DataDocument> createNewDataProcessAssetInventory(CreateInventoryAssetRequest request, Document document) {
        return dataDocumentRepository.saveAll(contructionDataDocumentAssetInventory(request, document));
    }

    private List<DataDocument> contructionDataDocumentAssetInventory(CreateInventoryAssetRequest request, Document document) {
        List<DataDocument> dataDocuments = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (AssetDetailInventoryRequest data : request.getAssetDetail()){
            DataDocument dataDocument = new DataDocument();
            dataDocument.setIdAsset(data.getIdAsset());
            dataDocument.setIdDocument(document.getIdDocument());
            dataDocument.setTimeCreated(timeCurrent);
            dataDocument.setTimeModified(timeCurrent);
            dataDocument.setStatus(Constants.STATUS_DATA_DOCUMENT_ACTIVE);
            dataDocuments.add(dataDocument);
        }
        return dataDocuments;
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
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
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
