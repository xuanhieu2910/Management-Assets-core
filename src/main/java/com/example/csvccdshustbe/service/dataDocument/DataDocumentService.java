package com.example.csvccdshustbe.service.dataDocument;

import com.example.csvccdshustbe.entity.DataDocument;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessAssetResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DataDocumentService {

    List<DataDocument> findDataProcessIncreaseAssetByIdsAsset(List<Integer> idsAsset);
    void createNewDataProcessAsset(CreateIncreaseAssetRequest request, Document document)
            throws ValidateFiledException;
     Page<FindAllProcessAssetResponse> findAllDataProcessAsset(FindAllProcessAssetRequest request);
}
