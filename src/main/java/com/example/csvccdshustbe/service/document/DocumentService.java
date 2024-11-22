package com.example.csvccdshustbe.service.document;

import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.document.FindAllDocumentAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetDecreaseRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetIncreaseRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetInventoryRequest;
import com.example.csvccdshustbe.response.document.FindAllDocumentAssetResponse;
import com.example.csvccdshustbe.response.document.FindDetailsDocumentResponse;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetDecreaseResponse;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetIncreaseResponse;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetInventoryResponse;
import org.springframework.data.domain.Page;

public interface DocumentService {

    Document findDocumentByCodeAndIdDepartment(String code, Integer idDepartment);
    Document findDocumentByIdDepartment(Integer idDepartment);
    Document saveDocument(Document document) throws ValidateFiledException;
    String generateCodeDocument(String typeGenerate);
    Page<FindAllDocumentAssetResponse> findAllDocumentByAsset(FindAllDocumentAssetRequest request);
    FindDetailsDocumentResponse findDetailsDocumentByCodeDocument(String codeDocument);

    Page<FindAllProcessAssetIncreaseResponse> findAllDataProcessAssetIncrease(FindAllProcessAssetIncreaseRequest request);
    Page<FindAllProcessAssetInventoryResponse> findAllDataProcessAssetInventory(FindAllProcessAssetInventoryRequest request);

    Page<FindAllProcessAssetDecreaseResponse> findAllDataProcessAssetDecrease(FindAllProcessAssetDecreaseRequest request);
}
