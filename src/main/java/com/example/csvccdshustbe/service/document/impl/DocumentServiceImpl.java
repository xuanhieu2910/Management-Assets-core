package com.example.csvccdshustbe.service.document.impl;

import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.repository.document.DocumentRepository;
import com.example.csvccdshustbe.service.document.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Override
    public Document findDocumentByCodeAndIdDepartment(String code, Integer idDepartment) {
        Optional<Document> document = documentRepository.findDocumentByCodeAndIdDepartment(code, idDepartment);
        return document.orElse(null);
    }
}
