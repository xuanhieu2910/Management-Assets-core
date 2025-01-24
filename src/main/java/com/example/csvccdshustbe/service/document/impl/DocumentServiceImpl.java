package com.example.csvccdshustbe.service.document.impl;

import com.example.csvccdshustbe.dto.document.FindAllDocumentAssetDto;
import com.example.csvccdshustbe.dto.document.FindDetailsDocumentDto;
import com.example.csvccdshustbe.dto.document.tool.FindAllDocumentToolDto;
import com.example.csvccdshustbe.dto.fluctuatingSituationAsset.AssetsFluctuatingSituationAssetDto;
import com.example.csvccdshustbe.dto.process.*;
import com.example.csvccdshustbe.dto.state.BluePrintStateDto;
import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.repository.document.DocumentRepository;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import com.example.csvccdshustbe.request.document.UpdateInventoryAssetRequest;
import com.example.csvccdshustbe.request.document.tool.FindAllDocumentToolRequest;
import com.example.csvccdshustbe.request.document.tool.UpdateInventoryToolRequest;
import com.example.csvccdshustbe.request.process.*;
import com.example.csvccdshustbe.response.document.FindAllDocumentAssetResponse;
import com.example.csvccdshustbe.response.document.FindDetailsDocumentResponse;
import com.example.csvccdshustbe.response.document.tool.FindAllDocumentToolDecreaseResponse;
import com.example.csvccdshustbe.response.document.tool.FindAllDocumentToolDocumentInventoryResponse;
import com.example.csvccdshustbe.response.document.tool.FindAllDocumentToolDocumentUpdateInventoryResponse;
import com.example.csvccdshustbe.response.document.tool.FindAllDocumentToolIncreaseResponse;
import com.example.csvccdshustbe.response.process.*;
import com.example.csvccdshustbe.response.state.BluePrintStateResponse;
import com.example.csvccdshustbe.service.assetProcess.AssetProcessService;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.document.DocumentService;
import com.example.csvccdshustbe.service.fluctuatingSituationAssetService.FluctuatingSituationAssetService;
import com.example.csvccdshustbe.service.fluctuatingSituationService.FluctuatingSituationService;
import com.example.csvccdshustbe.service.fluctuatingSituationToolService.FluctuatingSituationToolService;
import com.example.csvccdshustbe.service.toolProcess.ToolProcessService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    AssetProcessService assetProcessService;
    @Autowired
    FluctuatingSituationService fluctuatingSituationService;
    @Autowired
    FluctuatingSituationAssetService fluctuatingSituationAssetService;
    @Autowired
    ToolProcessService toolProcessService;
    @Autowired
    FluctuatingSituationToolService fluctuatingSituationToolService;


    @Override
    public Document findDocumentByCodeAndIdDepartment(String code, Integer idDepartment) {
        Optional<Document> document = documentRepository.findDocumentByCodeAndIdDepartment(code, idDepartment);
        return document.orElse(null);
    }

    @Override
    public Document findDocumentByIdDepartment(Integer idDepartment) {
        Optional<Document> document = documentRepository.findDocumentByIdDepartment(idDepartment);
        return document.orElse(null);
    }

    @Override
    public Document saveDocument(Document document){
        return documentRepository.save(document);
    }

    @Override
    public String generateCodeDocument(String typeGenerate) {
        int minLength = 4;
        Integer idDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdDepartmentCurrent();
        Department department =
                departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        String prefix = null;
        int codeValueCurrent = 1;
        if (StringUtils.isNotBlank(department.getCode())){
            prefix = typeGenerate + Constants.PREFIX_DOCUMENT + department.getCode();
        } else {
            prefix = typeGenerate + Constants.PREFIX_DOCUMENT;
        }
        Document document = findDocumentByIdDepartment(idDepartment);
        if (document == null) {
            return prefix + String.format("%0" + minLength + "d", codeValueCurrent) + "-" + String.valueOf(new Date().getTime());
        }
        String codeDocument = document.getCode().split("-")[0];
        codeValueCurrent = Integer.parseInt(codeDocument.replaceAll(ValueUtil.PATTERN_NON_NUMBER, ""));
        if (String.valueOf(codeValueCurrent).length() > minLength) {
            minLength = minLength + 2;
        }
        return prefix + String.format("%0" + minLength + "d",(codeValueCurrent + 1)) + "-" + String.valueOf(new Date().getTime());
    }
    @Override
    public Page<FindAllDocumentAssetResponse> findAllDocumentByAsset(FindAllDocumentAssetRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setIdsDepartmentOriginal(request);
        Page<FindAllDocumentAssetDto> findAllDocumentAssetDtos = documentRepository.findAllDocumentAssetDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllDocumentAssetResponse(findAllDocumentAssetDtos.get().collect(Collectors.toList())),
                pageable, findAllDocumentAssetDtos.getTotalElements());
    }

    @Override
    public FindDetailsDocumentResponse findDetailsDocumentByCodeDocument(String codeDocument) {
        List<Integer> idsDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        Optional<FindDetailsDocumentDto> detailsDocumentDto = documentRepository.findDetailDocumentByCodeDocument(codeDocument,idsDepartment);
        if (detailsDocumentDto.isEmpty()){
            throw new NotFoundException("Don't exist document by code!");
        }
        return convertToFindDetailsDocumentResponse(detailsDocumentDto.get());
    }

    private FindDetailsDocumentResponse convertToFindDetailsDocumentResponse(FindDetailsDocumentDto findDetailsDocumentDto) {
        FindDetailsDocumentResponse response = new FindDetailsDocumentResponse();
        response.setIdDocument(findDetailsDocumentDto.getIdDocument());
        response.setCodeDocument(findDetailsDocumentDto.getCodeDocument());
        response.setFullName(findDetailsDocumentDto.getFullName());
        response.setUserName(findDetailsDocumentDto.getUserName());
        response.setDescription(findDetailsDocumentDto.getDescription());
        response.setStatus(findDetailsDocumentDto.getStatus());
        response.setTimeCreated(DateUtil.formatToPattern(new Date(findDetailsDocumentDto.getTimeCreated()), DateUtil.DATE_FORMAT));
        response.setTimeModified(DateUtil.formatToPattern(new Date(findDetailsDocumentDto.getTimeModified()), DateUtil.DATE_FORMAT));
        response.setTimeIncrease(findDetailsDocumentDto.getTimeIncrease());
        response.setTimeDocument(findDetailsDocumentDto.getTimeDocument());
        response.setIdDepartment(findDetailsDocumentDto.getIdDepartment());
        response.setCodeDepartment(findDetailsDocumentDto.getCodeDepartment());
        response.setNameDepartment(findDetailsDocumentDto.getNameDepartment());
        response.setStatusDocument(findDetailsDocumentDto.getStatusDocument());
        response.setIdProcess(findDetailsDocumentDto.getIdProcess());
        List<BluePrintStateResponse> bluePrintStateResponses = new ArrayList<>();
        for (BluePrintStateDto printStateDto : findDetailsDocumentDto.getBluePrintStateDto()){
            BluePrintStateResponse printStateResponse = new BluePrintStateResponse();
            printStateResponse.setIdState(printStateDto.getIdState());
            printStateResponse.setStatus(printStateDto.getStatus());
            printStateResponse.setCodeTypeState(printStateDto.getCodeTypeState());
            printStateResponse.setIdTypeState(printStateDto.getIdTypeState());
            printStateResponse.setNameTypeState(printStateDto.getNameTypeState());
            bluePrintStateResponses.add(printStateResponse);
        }
        response.setStates(bluePrintStateResponses);
        return response;
    }

    private List<FindAllDocumentAssetResponse> convertToFindAllDocumentAssetResponse(List<FindAllDocumentAssetDto> collect) {
        List<FindAllDocumentAssetResponse> responses = new ArrayList<>();
        for (FindAllDocumentAssetDto dto : collect) {
            FindAllDocumentAssetResponse response = new FindAllDocumentAssetResponse();
            response.setCodeDocument(dto.getCodeDocument());
            response.setCodeTypeProcess(dto.getCodeTypeProcess());
            response.setNameTypeProcess(dto.getNameTypeProcess());
            response.setNameUserCreate(dto.getNameUserCreate());
            response.setStatus(dto.getStatus());
            response.setDescription(dto.getDescription());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            responses.add(response);
        }
        return responses;
    }

    private void setIdsDepartmentOriginal(FindAllDocumentAssetRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
    }

    @Override
    public Page<FindAllProcessAssetIncreaseResponse> findAllDataProcessAssetIncrease(FindAllProcessAssetIncreaseRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllProcessAssetIncreaseDto> findAllProcessAssetDtos =
                documentRepository.findAllProcessAssetIncreaseDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetIncreaseResponse(findAllProcessAssetDtos.stream().toList()),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }

    @Override
    public Page<FindAllProcessAssetInventoryResponse>
    findAllDataProcessAssetDocumentInventory(FindAllProcessAssetDocumentInventoryRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllProcessAssetInventoryDto> findAllProcessAssetDtos =
                documentRepository.findAllProcessAssetDocumentInventoryDtoByIdsDepartment(request, pageable);
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

    @Override
    public Page<FindAllProcessAssetDecreaseResponse> findAllDataProcessAssetDecrease(FindAllProcessAssetDecreaseRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllProcessAssetDecreaseDto> findAllProcessAssetDtos =
                documentRepository.findAllProcessAssetDecreaseDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetDecreaseResponse(findAllProcessAssetDtos.stream().toList()),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }

    private List<FindAllProcessAssetDecreaseResponse>
    convertToFindAllProcessAssetDecreaseResponse(List<FindAllProcessAssetDecreaseDto> collect) {
        List<FindAllProcessAssetDecreaseResponse> responses = new ArrayList<>();
        for (FindAllProcessAssetDecreaseDto dto : collect) {
            FindAllProcessAssetDecreaseResponse response = new FindAllProcessAssetDecreaseResponse();
            response.setCodeDocument(dto.getCodeDocument());
            response.setIdUserCreate(dto.getIdUserCreate());
            response.setCodeUserCreate(dto.getCodeUserCreate());
            response.setNameUserCreate(dto.getNameUserCreate());
            response.setStatus(dto.getStatus());
            response.setTimeDecrease(dto.getTimeDecrease());

            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setTimeDocument(dto.getTimeDocument());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public Page<FindAllProcessAssetChangeResponse> findAllDataProcessAssetChange(FindAllProcessAssetChangeRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllProcessAssetChangeDto> findAllProcessAssetDtos =
                documentRepository.findAllProcessAssetChangeDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetChangeResponse(findAllProcessAssetDtos.stream().toList()),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }

    @Override
    public Page<FindAllProcessAssetRevaluationResponse> findAllDataProcessAssetRevaluation(FindAllProcessAssetRevaluationRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllProcessAssetRevaluationDto> findAllProcessAssetDtos =
                documentRepository.findAllProcessAssetRevaluationDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetRevaluationResponse(findAllProcessAssetDtos.stream().toList()),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }

    @Override
    public Document findDocumentByIdProcess(Integer idProcess) {
        Optional<Document> document = documentRepository.findDocumentByIdProcess(idProcess);
        if (document.isEmpty()){
            throw new NotFoundException("Don't exits document by id process!");
        }
        return document.get();
    }

    @Override
    public Document findDocumentByCodeDocument(String codeDocument) {
        Optional<Document> document =
                documentRepository.findDocumentByCodeDocumentAndStatus(codeDocument,
                        Constants.STATUS_DOCUMENT_CAN_CHANGE_OR_UPDATE);
        if (document.isEmpty()) {
            throw new NotFoundException("Don't exits document!");
        }
        return document.get();
    }


    @Transactional
    @Override
    public void updateInventoryDraftTool(UpdateInventoryToolRequest request) {
        Document document = findDocumentByCodeDocument(request.getCodeDocument());
        documentRepository.save(updateInformationDocument(request,document));
        if (!CollectionUtils.isEmpty(request.getToolProcess().getToolProcessRequests())) {
            toolProcessService.updateListToolProcessByIdProcess(request.getToolProcess().getToolProcessRequests(),
                    document.getIdProcess());
        }
    }

    @Transactional
    @Override
    public void updateInventoryFinishTool(UpdateInventoryToolRequest request) {
        Document document = findDocumentByCodeDocument(request.getCodeDocument());
        documentRepository.save(updateInformationDocument(request,document));
        if (!CollectionUtils.isEmpty(request.getToolProcess().getToolProcessRequests())) {
            toolProcessService.updateListToolProcessByIdProcess(request.getToolProcess().getToolProcessRequests(),
                    document.getIdProcess());
        }
        createFluctuatingSituation(document.getIdProcess(), Constants.TYPE_FLUCTUATING_SITUATION_DETAIL_TOOL);
    }

    @Transactional
    @Override
    public void updateInventoryDraftAsset(UpdateInventoryAssetRequest request) {
        Document document = findDocumentByCodeDocument(request.getCodeDocument());
        documentRepository.save(updateInformationDocument(request,document));
        if (!CollectionUtils.isEmpty(request.getAssetProcess().getAssets())) {
            assetProcessService.updateListAssetProcessByIdProcess(request.getAssetProcess(), document.getIdProcess());
        }
    }


    @Transactional
    @Override
    public void updateInventoryFinishAsset(UpdateInventoryAssetRequest request) {
        Document document = findDocumentByCodeDocument(request.getCodeDocument());
        documentRepository.save(updateInformationDocument(request,document));
        if (!CollectionUtils.isEmpty(request.getAssetProcess().getAssets())) {
            assetProcessService.updateListAssetProcessByIdProcess(request.getAssetProcess(), document.getIdProcess());
        }
        createFluctuatingSituation(document.getIdProcess(), Constants.TYPE_FLUCTUATING_SITUATION_DETAIL_ASSET);
    }

    private Document updateInformationDocument(UpdateInventoryAssetRequest request, Document document) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        document.setTimeModified(String.valueOf(new Date().getTime()));
        document.setIdUserModified(csvcUser.getIdUser());
        if (request.isUpdateFinished()) {
            document.setStatus(Constants.STATUS_DOCUMENT_CAN_NOT_CHANGE_OR_UPDATE);
        }
        return document;
    }

    private Document updateInformationDocument(UpdateInventoryToolRequest request, Document document) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        document.setTimeModified(String.valueOf(new Date().getTime()));
        document.setIdUserModified(csvcUser.getIdUser());
        if (request.isUpdateFinished()) {
            document.setStatus(Constants.STATUS_DOCUMENT_CAN_NOT_CHANGE_OR_UPDATE);
        }
        return document;
    }

    private void createFluctuatingSituation(Integer idProcess, Integer typeFluctuatingSituationDetail) {
        FluctuatingSituation fluctuatingSituation = constructionFluctuatingSituation(idProcess, typeFluctuatingSituationDetail);
        if (typeFluctuatingSituationDetail.equals(Constants.TYPE_FLUCTUATING_SITUATION_DETAIL_ASSET)) {
            createFluctuatingSituationAsset(fluctuatingSituation, idProcess);
        } else if (typeFluctuatingSituationDetail.equals(Constants.TYPE_FLUCTUATING_SITUATION_DETAIL_TOOL)){
            createFluctuatingSituationTool(fluctuatingSituation, idProcess);
        }

    }

    private void createFluctuatingSituationTool(FluctuatingSituation fluctuatingSituation, Integer idProcess) {
        List<ToolProcess> toolProcesses =
                toolProcessService.findToolProcessByIdProcessAndStatusFluctuationSituation(idProcess,
                        Arrays.asList(Constants.TYPE_FLUCTUATING_SITUATION_DECLARE,
                                Constants.TYPE_FLUCTUATING_SITUATION_INCREASE,
                                Constants.TYPE_FLUCTUATING_SITUATION_DECREASE));
        storeFluctuatingSituationTool(fluctuatingSituation, toolProcesses);
    }

    private void storeFluctuatingSituationTool(FluctuatingSituation fluctuatingSituation, List<ToolProcess> toolProcesses) {
        List<FluctuatingSituationTool> fluctuatingSituationTools = new ArrayList<>();
        for (ToolProcess toolProcess : toolProcesses){
            fluctuatingSituationTools.add(constructionFluctuatingSituationTool(toolProcess, fluctuatingSituation));
        }
        fluctuatingSituationToolService.saveAll(fluctuatingSituationTools);
    }

    private FluctuatingSituationTool constructionFluctuatingSituationTool(ToolProcess toolProcess,
                                                                          FluctuatingSituation fluctuatingSituation) {
        String timeCurrent = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FluctuatingSituationTool fluctuatingSituationTool = new FluctuatingSituationTool();
        fluctuatingSituationTool.setIdTool(toolProcess.getIdTool());
        fluctuatingSituationTool.setIdProcess(toolProcess.getIdProcess());
        fluctuatingSituationTool.setStatus(Constants.STATUS_FLUCTUATING_SITUATION_TOOL_NOT_FINISH);
        fluctuatingSituationTool.setType(toolProcess.getStatus());
        fluctuatingSituationTool.setTimeCreated(timeCurrent);
        fluctuatingSituationTool.setTimeModified(timeCurrent);
        fluctuatingSituationTool.setIdUserCreated(csvcUser.getIdUser());
        fluctuatingSituationTool.setIdUserModified(csvcUser.getIdUser());
        fluctuatingSituationTool.setIdFluctuatingSituation(fluctuatingSituation.getIdFluctuatingSituation());
        return fluctuatingSituationTool;
    }

    private void createFluctuatingSituationAsset(FluctuatingSituation fluctuatingSituation, Integer idProcess) {
        List<AssetsFluctuatingSituationAssetDto> fluctuatingSituationAssetDtos =
                assetProcessService.findAssetsToFluctuatingSituationByIdProcess(idProcess);
        storeFluctuatingSituationAsset(fluctuatingSituation, fluctuatingSituationAssetDtos);
    }

    private void storeFluctuatingSituationAsset(FluctuatingSituation fluctuatingSituation,
                                                     List<AssetsFluctuatingSituationAssetDto> fluctuatingSituationAssetDtos) {
        List<FluctuatingSituationAsset>  fluctuatingSituationAssets = new ArrayList<>();
        for (AssetsFluctuatingSituationAssetDto assetProcessRequest : fluctuatingSituationAssetDtos) {
            fluctuatingSituationAssets.add(constructionFluctuatingSituationAsset(fluctuatingSituation,
                    assetProcessRequest));

        }
        fluctuatingSituationAssetService.saveAllFluctuatingSituationAsset(fluctuatingSituationAssets);
    }

    private FluctuatingSituationAsset constructionFluctuatingSituationAsset(FluctuatingSituation fluctuatingSituation,
                                                                            AssetsFluctuatingSituationAssetDto assetsFluctuatingSituationAssetDto) {
        String timeCurrent = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FluctuatingSituationAsset fluctuatingSituationAsset = new FluctuatingSituationAsset();
        fluctuatingSituationAsset.setIdAsset(assetsFluctuatingSituationAssetDto.getIdAsset());
        fluctuatingSituationAsset.setIdProcess(fluctuatingSituation.getIdProcess());
        fluctuatingSituationAsset.setStatus(Constants.STATUS_FLUCTUATING_SITUATION_ASSET_NOT_FINISH);
        fluctuatingSituationAsset.setType(assetsFluctuatingSituationAssetDto.getTypeFluctuatingSituationAsset());
        fluctuatingSituationAsset.setTimeCreated(timeCurrent);
        fluctuatingSituationAsset.setTimeModified(timeCurrent);
        fluctuatingSituationAsset.setIdUserModified(csvcUser.getIdUser());
        fluctuatingSituationAsset.setIdFluctuatingSituation(fluctuatingSituation.getIdFluctuatingSituation());
        return fluctuatingSituationAsset;
    }


    private FluctuatingSituation constructionFluctuatingSituation(Integer idProcess, Integer typeFluctuatingSituation) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String timeCurrent = String.valueOf(new Date().getTime());
        FluctuatingSituation fluctuatingSituation = new FluctuatingSituation();
        fluctuatingSituation.setIdProcess(idProcess);
        fluctuatingSituation.setStatus(Constants.STATUS_FLUCTUATING_SITUATION_NOT_FINISH);
        fluctuatingSituation.setTimeCreated(timeCurrent);
        fluctuatingSituation.setTimeModified(timeCurrent);
        fluctuatingSituation.setIdUserModified(csvcUser.getIdUser());
        fluctuatingSituation.setType(typeFluctuatingSituation);
        return fluctuatingSituationService.saveFluctuatingSituation(fluctuatingSituation);
    }

    @Override
    public Page<FindAllProcessAssetUpdateInventoryResponse>
    findAllDataProcessAssetUpdateInventory(FindAllProcessAssetUpdateInventoryRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllProcessAssetUpdateInventoryDto> findAllProcessAssetDtos =
                documentRepository.findAllProcessAssetUpdateInventoryDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllProcessAssetUpdateInventoryResponse(findAllProcessAssetDtos.stream().toList()),
                pageable, findAllProcessAssetDtos.getTotalElements());
    }

    @Override
    public Page<FindAllDocumentToolIncreaseResponse>
    findAllDocumentToolIncrease(FindAllDocumentToolRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllDocumentToolDto> findAllDocumentToolDtos =
                documentRepository.findAllDocumentToolIncreaseDtoByIdsDepartment(request,pageable);
        return new PageImpl<>(convertToFindAllDocumentToolIncrease(findAllDocumentToolDtos.getContent()),
                pageable, findAllDocumentToolDtos.getTotalElements());
    }

    @Override
    public Page<FindAllDocumentToolDecreaseResponse> findAllDocumentToolDecrease(FindAllDocumentToolRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllDocumentToolDto> findAllDocumentToolDtos =
                documentRepository.findAllDocumentToolDecreaseDtoByIdsDepartment(request,pageable);
        return new PageImpl<>(convertToFindAllDocumentToolDecrease(findAllDocumentToolDtos.getContent()),
                pageable, findAllDocumentToolDtos.getTotalElements());
    }

    @Override
    public Page<FindAllDocumentToolDocumentInventoryResponse>
    findAllDocumentToolDocumentInventory(FindAllDocumentToolRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllDocumentToolDto> findAllToolDtos =
                documentRepository.findAllToolDocumentInventoryDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllToolInventoryResponse(findAllToolDtos.stream().toList()),
                pageable, findAllToolDtos.getTotalElements());
    }

    @Override
    public Page<FindAllDocumentToolDocumentUpdateInventoryResponse> findAllDocumentToolDocumentUpdateInventory(FindAllDocumentToolRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllDocumentToolDto> findAllToolUpdateInventoryDtos =
                documentRepository.findAllToolDocumentUpdateInventoryDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllToolUpdateInventoryResponse(findAllToolUpdateInventoryDtos.stream().toList()),
                pageable, findAllToolUpdateInventoryDtos.getTotalElements());
    }

    private List<FindAllDocumentToolDocumentUpdateInventoryResponse> convertToFindAllToolUpdateInventoryResponse(List<FindAllDocumentToolDto> content) {
        List<FindAllDocumentToolDocumentUpdateInventoryResponse> responses = new ArrayList<>();
        for (FindAllDocumentToolDto toolDto : content) {
            FindAllDocumentToolDocumentUpdateInventoryResponse response = new FindAllDocumentToolDocumentUpdateInventoryResponse();
            response.setCodeDocument(toolDto.getCodeDocument());
            response.setIdUserCreate(toolDto.getIdUserCreate());
            response.setFullNameCreate(toolDto.getFullNameUser());
            response.setNameUserCreate(toolDto.getNameUserCreate());
            response.setCodeDepartment(toolDto.getCodeDepartment());
            response.setNameDepartment(toolDto.getNameDepartment());
            response.setStatus(toolDto.getStatus());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(toolDto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(toolDto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setTimeDocument(toolDto.getTimeDocument());
            response.setTimeUpdateInventory(toolDto.getTimeIncrease());
            response.setDescription(toolDto.getDescription());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllDocumentToolDocumentInventoryResponse> convertToFindAllToolInventoryResponse(List<FindAllDocumentToolDto> content) {
        List<FindAllDocumentToolDocumentInventoryResponse> responses = new ArrayList<>();
        for (FindAllDocumentToolDto toolDto : content) {
            FindAllDocumentToolDocumentInventoryResponse response = new FindAllDocumentToolDocumentInventoryResponse();
            response.setCodeDocument(toolDto.getCodeDocument());
            response.setIdUserCreate(toolDto.getIdUserCreate());
            response.setFullNameCreate(toolDto.getFullNameUser());
            response.setNameUserCreate(toolDto.getNameUserCreate());
            response.setCodeDepartment(toolDto.getCodeDepartment());
            response.setNameDepartment(toolDto.getNameDepartment());
            response.setStatus(toolDto.getStatus());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(toolDto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(toolDto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setTimeDocument(toolDto.getTimeDocument());
            response.setTimeInventory(toolDto.getTimeIncrease());
            response.setDescription(toolDto.getDescription());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllDocumentToolDecreaseResponse> convertToFindAllDocumentToolDecrease(List<FindAllDocumentToolDto> content) {
        List<FindAllDocumentToolDecreaseResponse> responses = new ArrayList<>();
        for (FindAllDocumentToolDto toolDto : content) {
            FindAllDocumentToolDecreaseResponse response = new FindAllDocumentToolDecreaseResponse();
            response.setCodeDocument(toolDto.getCodeDocument());
            response.setIdUserCreate(toolDto.getIdUserCreate());
            response.setFullNameCreate(toolDto.getFullNameUser());
            response.setNameUserCreate(toolDto.getNameUserCreate());
            response.setCodeDepartment(toolDto.getCodeDepartment());
            response.setNameDepartment(toolDto.getNameDepartment());
            response.setStatus(toolDto.getStatus());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(toolDto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(toolDto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setTimeDocument(toolDto.getTimeDocument());
            response.setTimeDecrease(toolDto.getTimeIncrease());
            response.setDescription(toolDto.getDescription());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllDocumentToolIncreaseResponse> convertToFindAllDocumentToolIncrease(List<FindAllDocumentToolDto> content) {
        List<FindAllDocumentToolIncreaseResponse> responses = new ArrayList<>();
        for (FindAllDocumentToolDto toolDto : content) {
            FindAllDocumentToolIncreaseResponse response = new FindAllDocumentToolIncreaseResponse();
            response.setCodeDocument(toolDto.getCodeDocument());
            response.setIdUserCreate(toolDto.getIdUserCreate());
            response.setFullNameCreate(toolDto.getFullNameUser());
            response.setNameUserCreate(toolDto.getNameUserCreate());
            response.setCodeDepartment(toolDto.getCodeDepartment());
            response.setNameDepartment(toolDto.getNameDepartment());
            response.setStatus(toolDto.getStatus());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(toolDto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(toolDto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setTimeDocument(toolDto.getTimeDocument());
            response.setTimeIncrease(toolDto.getTimeIncrease());
            response.setDescription(toolDto.getDescription());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllProcessAssetChangeResponse>
    convertToFindAllProcessAssetChangeResponse(List<FindAllProcessAssetChangeDto> collect) {
        List<FindAllProcessAssetChangeResponse> responses = new ArrayList<>();
        for (FindAllProcessAssetChangeDto dto : collect) {
            FindAllProcessAssetChangeResponse response = new FindAllProcessAssetChangeResponse();
            response.setCodeDocument(dto.getCodeDocument());
            response.setIdUserCreate(dto.getIdUserCreate());
            response.setCodeUserCreate(dto.getCodeUserCreate());
            response.setNameUserCreate(dto.getNameUserCreate());
            response.setStatus(dto.getStatus());
            response.setTimeChange(dto.getTimeChange());

            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setTimeDocument(dto.getTimeDocument());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllProcessAssetUpdateInventoryResponse>
    convertToFindAllProcessAssetUpdateInventoryResponse(List<FindAllProcessAssetUpdateInventoryDto> collect) {
        List<FindAllProcessAssetUpdateInventoryResponse> responses = new ArrayList<>();
        for (FindAllProcessAssetUpdateInventoryDto dto : collect) {
            FindAllProcessAssetUpdateInventoryResponse response = new FindAllProcessAssetUpdateInventoryResponse();
            response.setCodeDocument(dto.getCodeDocument());
            response.setIdUserCreate(dto.getIdUserCreate());
            response.setCodeUserCreate(dto.getCodeUserCreate());
            response.setNameUserCreate(dto.getNameUserCreate());
            response.setStatus(dto.getStatus());
            response.setTimeInventory(dto.getTimeInventory());
            response.setIdProcess(dto.getIdProcess());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(new Date(dto.getTimeCreated()), DateUtil.DATE_FORMAT));
            response.setTimeModified(DateUtil.formatToPattern(new Date(dto.getTimeModified()), DateUtil.DATE_FORMAT));
            response.setTimeDocument(dto.getTimeDocument());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllProcessAssetRevaluationResponse>
    convertToFindAllProcessAssetRevaluationResponse(List<FindAllProcessAssetRevaluationDto> collect) {
        List<FindAllProcessAssetRevaluationResponse> responses = new ArrayList<>();
        for (FindAllProcessAssetRevaluationDto dto : collect) {
            FindAllProcessAssetRevaluationResponse response = new FindAllProcessAssetRevaluationResponse();
            response.setCodeDocument(dto.getCodeDocument());
            response.setIdUserCreate(dto.getIdUserCreate());
            response.setCodeUserCreate(dto.getCodeUserCreate());
            response.setNameUserCreate(dto.getNameUserCreate());
            response.setStatus(dto.getStatus());
            response.setTimeRevaluation(dto.getTimeRevaluation());

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
