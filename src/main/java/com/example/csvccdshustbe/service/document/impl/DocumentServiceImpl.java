package com.example.csvccdshustbe.service.document.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.document.DocumentRepository;
import com.example.csvccdshustbe.request.process.document.CreateDocumentRequest;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.document.DocumentService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.utility.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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
    public Document createNewDocument(CreateDocumentRequest request) throws ValidateFiledException {
        validateCreateNewDocument(request);
        Integer idDepartment = csvcUserService.getInformationUser().getIdDepartment();
        if (findDocumentByCodeAndIdDepartment(request.getCodeDocument(), idDepartment) != null){
            throw new ValidateFiledException("Exist document by code!");
        }
        return documentRepository.save(contructDocument(request,idDepartment));
    }

    private Document contructDocument(CreateDocumentRequest request, Integer idDepartment) {
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(request.getCodeDocument());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(request.getTimeIncrease());
        document.setTimeDocument(request.getTimeDocument());
        document.setIdDepartment(idDepartment);
        return document;
    }

    @Override
    public String generateCodeDocument() {
        Integer idDepartment = csvcUserService.getInformationUser().getIdDepartment();
        Department department =
                departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        String prefix = null;
        if (StringUtils.isNotBlank(department.getCode())){
            prefix = department.getCode();
        }
        prefix = Constants.PREFIX_DOCUMENT;
        Document document = findDocumentByIdDepartment(idDepartment);
        int codeValueCurrent = Integer.parseInt(document.getCode().replace(prefix,""));
        return prefix + (codeValueCurrent + 1);
    }

    private void validateCreateNewDocument(CreateDocumentRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeDocument()) || StringUtils.isBlank(request.getTimeIncrease())
        || StringUtils.isBlank(request.getTimeDocument())){
            throw new ValidateFiledException("Validate data create document!");
        }
    }
}
