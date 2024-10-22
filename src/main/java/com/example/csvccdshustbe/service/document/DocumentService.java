package com.example.csvccdshustbe.service.document;

import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.document.CreateDocumentRequest;

public interface DocumentService {

    Document findDocumentByCodeAndIdDepartment(String code, Integer idDepartment);
    Document findDocumentByIdDepartment(Integer idDepartment);
    Document createNewDocument(CreateDocumentRequest request) throws ValidateFiledException;
    String generateCodeDocument();
}
