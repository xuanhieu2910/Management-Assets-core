package com.example.csvccdshustbe.service.dataDocumentInventory;


import com.example.csvccdshustbe.entity.DataDocumentInventory;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.request.process.CreateInventoryAssetRequest;

import java.util.List;

public interface DataDocumentInventoryService {
    List<DataDocumentInventory> createNewDataDocumentInventories(CreateInventoryAssetRequest request, Document document);
}
