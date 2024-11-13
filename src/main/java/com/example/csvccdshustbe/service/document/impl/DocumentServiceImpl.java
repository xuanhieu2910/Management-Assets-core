package com.example.csvccdshustbe.service.document.impl;

import com.example.csvccdshustbe.dto.document.FindAllDocumentAssetDto;
import com.example.csvccdshustbe.dto.document.FindDetailsDocumentDto;
import com.example.csvccdshustbe.dto.state.BluePrintStateDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.repository.document.DocumentRepository;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import com.example.csvccdshustbe.response.document.FindAllDocumentAssetResponse;
import com.example.csvccdshustbe.response.document.FindDetailsDocumentResponse;
import com.example.csvccdshustbe.response.state.BluePrintStateResponse;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.document.DocumentService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DepartmentService departmentService;

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
    public String generateCodeDocument() {
        int minLength = 4;
        Integer idDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdDepartmentCurrent();
        Department department =
                departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        String prefix = null;
        int codeValueCurrent = 1;
        if (StringUtils.isNotBlank(department.getCode())){
            prefix = Constants.PREFIX_DOCUMENT + department.getCode();
        } else {
            prefix = Constants.PREFIX_DOCUMENT;
        }
        Document document = findDocumentByIdDepartment(idDepartment);
        if (document == null) {
            return prefix + String.format("%0" + minLength + "d", codeValueCurrent) + "-";
        }
        codeValueCurrent = Integer.parseInt(document.getCode().replace(prefix,"").split("-")[0]);
        if (String.valueOf(codeValueCurrent).length() > minLength) {
            minLength = minLength + 2;
        }
        return prefix + String.format("%0" + minLength + "d",(codeValueCurrent + 1)) + "-";
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

}
