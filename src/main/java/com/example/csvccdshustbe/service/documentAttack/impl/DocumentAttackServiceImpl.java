package com.example.csvccdshustbe.service.documentAttack.impl;

import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.department.DepartmentRepository;
import com.example.csvccdshustbe.repository.documentAttack.DocumentAttackRepository;
import com.example.csvccdshustbe.request.documentAttack.CreateDocumentAttackRequest;
import com.example.csvccdshustbe.request.documentAttack.UpdateDocumentAttackRequest;
import com.example.csvccdshustbe.response.documentAttack.FindAllDocumentAttackResponse;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentAttackServiceImpl implements DocumentAttackService {


    @Autowired
    DocumentAttackRepository documentAttackRepository;
    DepartmentRepository departmentRepository;
    public DocumentAttackServiceImpl(DocumentAttackRepository documentAttackRepository) {

        this.documentAttackRepository = documentAttackRepository;
    }
    @Override
    public List<FindAllDocumentAttackResponse> findAllDocumentAttackResponseByStatus(Integer status){
        return convertToFindAllDocumentAttack(documentAttackRepository.findAllDocumentAttackResponseByStatus(status));
    }

    private List<FindAllDocumentAttackResponse> convertToFindAllDocumentAttack(List<DocumentAttack> allDocumentAttackByStatus) {
        List<FindAllDocumentAttackResponse> responses = new ArrayList<>();
        for (DocumentAttack documentAttack : allDocumentAttackByStatus){
            FindAllDocumentAttackResponse response = new FindAllDocumentAttackResponse();
            response.setIdDocumentAttack(documentAttack.getIdDocumentAttack());
            response.setName(documentAttack.getName());
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
        ValueUtil.validateNumberOrCharacter(request.getName());
        Optional<DocumentAttack> documentAttackOptional = documentAttackRepository.findDocumentAttackByName(request.getName());
        if (documentAttackOptional.isPresent()) {
            throw new ValidateFiledException("Exits Document Attack by name Document Attack!");
        }
        if (StringUtils.isNotBlank(request.getCode())) {
            if (request.getCode().equals(documentAttackOptional.get().getCode())) {
                throw new ValidateFiledException("Exits Document Attack by code name");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getIdDepartment())) {

            Optional<Department> departmentOptional = departmentRepository.findDepartmentById(request.getIdDepartment());
            if (!departmentOptional.isPresent()) {
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
        if (!documentAttackOptional.isPresent()) {
            throw new NotFoundException("Don't exits document Attack by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!documentAttackOptional.get().getName().equals(request.getName())) {

            if (StringUtils.isNotBlank(request.getName())){
                ValueUtil.validateNumberOrCharacter(request.getName());
            }
        }
        if (StringUtils.isNotBlank(request.getCode())){
            ValueUtil.validateNumberOrCharacter(request.getCode());
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
    public void deleteDocumentAttackByIdDA(Integer idDocumentAttack) {
        Optional<DocumentAttack> documentAttackOptional = documentAttackRepository.findDocumentAttackById(idDocumentAttack);
        if (!documentAttackOptional.isPresent()){
            throw new NotFoundException("Don't exits  document attack by id !");
        }
        documentAttackRepository.delete(documentAttackOptional.get());
    }
}
