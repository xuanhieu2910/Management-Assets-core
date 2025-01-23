package com.example.csvccdshustbe.service.document;

import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
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
import org.springframework.data.domain.Page;

public interface DocumentService {

    Document findDocumentByCodeAndIdDepartment(String code, Integer idDepartment);
    Document findDocumentByIdDepartment(Integer idDepartment);
    Document saveDocument(Document document) throws ValidateFiledException;
    String generateCodeDocument(String typeGenerate);
    Page<FindAllDocumentAssetResponse> findAllDocumentByAsset(FindAllDocumentAssetRequest request);
    FindDetailsDocumentResponse findDetailsDocumentByCodeDocument(String codeDocument);
    Page<FindAllProcessAssetIncreaseResponse> findAllDataProcessAssetIncrease(FindAllProcessAssetIncreaseRequest request);
    Page<FindAllProcessAssetInventoryResponse> findAllDataProcessAssetDocumentInventory(FindAllProcessAssetDocumentInventoryRequest request);

    Page<FindAllProcessAssetDecreaseResponse> findAllDataProcessAssetDecrease(FindAllProcessAssetDecreaseRequest request);

    Page<FindAllProcessAssetChangeResponse> findAllDataProcessAssetChange(FindAllProcessAssetChangeRequest request);
    Page<FindAllProcessAssetRevaluationResponse> findAllDataProcessAssetRevaluation(FindAllProcessAssetRevaluationRequest request);
    Document findDocumentByIdProcess(Integer idProcess);
    Document findDocumentByCodeDocument(String codeDocument);
    void updateInventoryDraftTool(UpdateInventoryToolRequest request);
    void updateInventoryFinishTool(UpdateInventoryToolRequest request);
    void updateInventoryDraftAsset(UpdateInventoryAssetRequest request);
    void updateInventoryFinishAsset(UpdateInventoryAssetRequest request);
    Page<FindAllProcessAssetUpdateInventoryResponse>
    findAllDataProcessAssetUpdateInventory(FindAllProcessAssetUpdateInventoryRequest findAllProcessAssetRequest);
    Page<FindAllDocumentToolIncreaseResponse> findAllDocumentToolIncrease(FindAllDocumentToolRequest findAllProcessAssetRequest);
    Page<FindAllDocumentToolDecreaseResponse> findAllDocumentToolDecrease(FindAllDocumentToolRequest findAllProcessAssetRequest);
    Page<FindAllDocumentToolDocumentInventoryResponse> findAllDocumentToolDocumentInventory(FindAllDocumentToolRequest findAllProcessAssetRequest);
    Page<FindAllDocumentToolDocumentUpdateInventoryResponse> findAllDocumentToolDocumentUpdateInventory(FindAllDocumentToolRequest findAllProcessAssetRequest);
}
