package com.example.csvccdshustbe.service.document.impl;

import com.example.csvccdshustbe.dto.document.FindAllDocumentAssetDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.document.DocumentRepository;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import com.example.csvccdshustbe.request.process.document.CreateDocumentRequest;
import com.example.csvccdshustbe.response.document.FindAllDocumentAssetResponse;
import com.example.csvccdshustbe.response.document.FindDetailsDocumentResponse;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.document.DocumentService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
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
    CsvcUserService csvcUserService;
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
        Integer idDepartment = csvcUserService.getInformationUser().getIdDepartment();
        Department department =
                departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        String prefix = null;
        if (StringUtils.isNotBlank(department.getCode())){
            prefix = department.getCode();
        } else {
            prefix = Constants.PREFIX_DOCUMENT;
        }
        Document document = findDocumentByIdDepartment(idDepartment);
        int codeValueCurrent = Integer.parseInt(document.getCode().replace(prefix,""));
        return prefix + (codeValueCurrent + 1);
    }

    @Override
    public Page<FindAllDocumentAssetResponse> findAllDocumentAsset(FindAllDocumentAssetRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setIdsDepartmentOriginal(request);
        Page<FindAllDocumentAssetDto> findAllDocumentAssetDtos = documentRepository.findAllDocumentAssetDtoByIdsDepartment(request, pageable);
        return new PageImpl<>(convertToFindAllDocumentAssetResponse(findAllDocumentAssetDtos.get().collect(Collectors.toList())),
                pageable, findAllDocumentAssetDtos.getTotalElements());
    }

    @Override
    public FindDetailsDocumentResponse findDetailsDocumentByCodeDocument(String codeDocument) {
        return null;
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
            response.setTimeCreated(DateUtil.formatToPattern(
                    DateUtil.formatDatePattern(dto.getTimeCreated(),
                            DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT_HH_MM));
            responses.add(response);
        }
        return responses;
    }

    private void setIdsDepartmentOriginal(FindAllDocumentAssetRequest request) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartmentOriginal(csvcUser.getIdsDepartmentCurrent());
    }

}
