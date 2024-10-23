package com.example.csvccdshustbe.service.document;

import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import com.example.csvccdshustbe.request.process.document.CreateDocumentRequest;
import com.example.csvccdshustbe.response.document.FindAllDocumentAssetResponse;
import org.springframework.data.domain.Page;

public interface DocumentService {

    Document findDocumentByCodeAndIdDepartment(String code, Integer idDepartment);
    Document findDocumentByIdDepartment(Integer idDepartment);
    Document createNewDocument(CreateDocumentRequest request) throws ValidateFiledException;
    String generateCodeDocument();

    Page<FindAllDocumentAssetResponse> findAllDocumentAsset(FindAllDocumentAssetRequest request);


}
