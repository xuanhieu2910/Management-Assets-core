package com.example.csvccdshustbe.service.documentAttack.impl;

import com.example.csvccdshustbe.dto.documentAttack.FindAllDocumentAttackDto;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.entity.DocumentAttack;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.department.DepartmentRepository;
import com.example.csvccdshustbe.repository.documentAttack.DocumentAttackRepository;
import com.example.csvccdshustbe.request.documentAttack.*;
import com.example.csvccdshustbe.response.documentAttack.FindAllDocumentAttackResponse;
import com.example.csvccdshustbe.response.documentAttack.FindAllDocumentAttackVisibleResponse;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.ObjectUtils;
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
public class DocumentAttackServiceImpl implements DocumentAttackService {


    @Autowired
    DocumentAttackRepository documentAttackRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Page<FindAllDocumentAttackVisibleResponse>
    findAllDocumentAttackVisibleResponse(FindAllDocumentAttackVisibleRequest request){
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<DocumentAttack> documentAttacks = documentAttackRepository.findAllDocumentAttackVisibleResponse(request, pageable);
        return new PageImpl<>(convertToFindAllDocumentAttackVisible(documentAttacks.get().collect(Collectors.toList())),
                    pageable, documentAttacks.getTotalElements());
    }

    @Override
    public Page<FindAllDocumentAttackResponse> findAllDocumentAttackResponse(FindAllDocumentAttackRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllDocumentAttackDto> documentAttacks = documentAttackRepository.findAllDocumentAttackResponse(request, pageable);
        return new PageImpl<>(convertToFindAllDocumentAttack(documentAttacks.get().collect(Collectors.toList())),
                pageable, documentAttacks.getTotalElements());
    }

    private List<FindAllDocumentAttackVisibleResponse> convertToFindAllDocumentAttackVisible(List<DocumentAttack> allDocumentAttackByStatus) {
        List<FindAllDocumentAttackVisibleResponse> responses = new ArrayList<>();
        for (DocumentAttack documentAttack : allDocumentAttackByStatus){
            FindAllDocumentAttackVisibleResponse response = new FindAllDocumentAttackVisibleResponse();
            response.setIdDocumentAttack(documentAttack.getIdDocumentAttack());
            response.setName(documentAttack.getName());
            response.setCode(documentAttack.getCode());
            responses.add(response);
        }
        return responses;
    }

    private List<FindAllDocumentAttackResponse> convertToFindAllDocumentAttack(List<FindAllDocumentAttackDto> allDocumentAttackByStatus) {
        List<FindAllDocumentAttackResponse> responses = new ArrayList<>();
        for (FindAllDocumentAttackDto documentAttack : allDocumentAttackByStatus){
            FindAllDocumentAttackResponse response = new FindAllDocumentAttackResponse();
            response.setIdDocumentAttack(documentAttack.getIdDocumentAttack());
            response.setName(documentAttack.getName());
            response.setCode(documentAttack.getCode());
            response.setNameDepartment(documentAttack.getNameDepartment());
            response.setDateDeterminationDocument(documentAttack.getDateDeterminationDocument());
            response.setVisible(documentAttack.getStatus());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void createDocumentAttack(CreateDocumentAttackRequest request) throws ValidateFiledException {
        validateDataCreateDocumentAttack(request);
        documentAttackRepository.save(contructDocumentAttack(request));
    }

    private void validateDataCreateDocumentAttack(CreateDocumentAttackRequest request) throws ValidateFiledException{
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<DocumentAttack> documentAttackOptional = documentAttackRepository.findDocumentAttackByName(request.getName());
        if (documentAttackOptional.isPresent()) {
            if (StringUtils.isNotBlank(request.getCode())) {
                if (request.getCode().equals(documentAttackOptional.get().getCode())) {
                    throw new ValidateFiledException("Exits Document Attack by code name");
                }
            }
            throw new ValidateFiledException("Exits Document Attack by name Document Attack!");
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {

            Optional<Department> departmentOptional = departmentRepository.findDepartmentById(request.getIdDepartment());
            if (departmentOptional.isEmpty()) {
                throw new ValidateFiledException("Don't exits department of Document Attack!");
            }
        }
    }
    private DocumentAttack contructDocumentAttack(CreateDocumentAttackRequest request) {
        DocumentAttack documentAttack = new DocumentAttack();
        documentAttack.setName(request.getName());
        documentAttack.setIdDepartment(request.getIdDepartment());
        documentAttack.setCode(request.getCode());
        documentAttack.setDateDeterminationDocument(request.getDateDeterminationDocument());
        documentAttack.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        documentAttack.setTimeCreated(timeCurrent);
        documentAttack.setTimeModified(timeCurrent);
        return documentAttack;
    }

    @Override
    public void updateDocumentAttack(UpdateDocumentAttackRequest request) throws ValidateFiledException {
        DocumentAttack documentAttack = validateDataUpdateDocumentAttack(request);
        documentAttackRepository.save(editDocumentAttack(documentAttack, request));
    }

    private DocumentAttack validateDataUpdateDocumentAttack(UpdateDocumentAttackRequest request) throws ValidateFiledException{
        Optional<DocumentAttack> documentAttackOptional = documentAttackRepository.findDocumentAttackById(request.getIdDocumentAttack());
        if (documentAttackOptional.isEmpty()) {
            throw new NotFoundException("Don't exits document Attack by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return documentAttackOptional.get();
    }
    private DocumentAttack editDocumentAttack(DocumentAttack documentAttack, UpdateDocumentAttackRequest request) {
        documentAttack.setName(request.getName());
        documentAttack.setIdDepartment(request.getIdDepartment());
        documentAttack.setCode(request.getCode());
        documentAttack.setDateDeterminationDocument(request.getDateDeterminationDocument());
        documentAttack.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        documentAttack.setTimeModified(timeModified);
        return documentAttack;
    }
    @Override
    public void deleteDocumentAttackByIdDA(Integer idDocumentAttack) throws NotFoundException, ValidateFiledException {
        Optional<DocumentAttack> documentAttackOptional = documentAttackRepository.findDocumentAttackById(idDocumentAttack);
        if (documentAttackOptional.isEmpty()){
            throw new NotFoundException("Don't exits  document attack by id !");
        }
        if (documentAttackRepository.isExitsAssetByIdDocumentAttack(idDocumentAttack)){
            throw new ValidateFiledException("Exits asset by id document, can't delete document attack!");
        }
        documentAttackRepository.delete(documentAttackOptional.get());
    }

    @Override
    public DocumentAttack findDocumentAttackByIdDocumentAndStatus(Integer idDocumentAttack, Integer status) {
        Optional<DocumentAttack>documentAttackOptional = documentAttackRepository.
                findDocumentAttackByIdDocumentAndStatus(idDocumentAttack, status);
        if (documentAttackOptional.isEmpty()) {
            throw new NotFoundException("Don't exits document attack!");
        }
        return documentAttackOptional.get();
    }

    @Override
    public void updateStatusDocumentAttack(UpdateStatusDocumentAttackRequest request) throws ValidateFiledException {
        Optional<DocumentAttack> documentAttack = documentAttackRepository.findDocumentAttackById(request.getIdDocumentAttack());
        if (documentAttack.isEmpty()) {
            throw new NotFoundException("Don't exits document attack!");
        }
        if (request.getStatus().equals(Constants.DOCUMENT_ATTACK_ACTIVE_STATUS) &&
            request.getStatus().equals(Constants.DOCUMENT_ATTACK_UN_ACTIVE_STATUS)) {
            throw new ValidateFiledException("Validate data update document attack!");
        }
        documentAttack.get().setStatus(request.getStatus());
        documentAttackRepository.save(documentAttack.get());
    }
}
