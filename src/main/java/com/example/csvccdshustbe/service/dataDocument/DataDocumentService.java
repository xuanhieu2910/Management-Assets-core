package com.example.csvccdshustbe.service.dataDocument;

import com.example.csvccdshustbe.entity.DataDocument;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetIncreaseRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetInventoryRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetIncreaseResponse;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetInventoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DataDocumentService {

    List<DataDocument> findDataProcessIncreaseAssetByIdsAsset(List<Integer> idsAsset);
    void createNewDataProcessAssetIncrease(CreateIncreaseAssetRequest request, Document document)
            throws ValidateFiledException;
     Page<FindAllProcessAssetIncreaseResponse> findAllDataProcessAssetIncrease(FindAllProcessAssetIncreaseRequest request);
     Page<FindAllProcessAssetInventoryResponse> findAllDataProcessAssetInventory(FindAllProcessAssetInventoryRequest request);
}
