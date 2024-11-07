package com.example.csvccdshustbe.service.dataDocumentInventory.impl;

import com.example.csvccdshustbe.entity.DataDocumentInventory;
import com.example.csvccdshustbe.entity.Document;
import com.example.csvccdshustbe.repository.dataDocumentInventory.DataDocumentInventoryRepository;
import com.example.csvccdshustbe.request.process.CreateInventoryAssetRequest;
import com.example.csvccdshustbe.request.process.asset.AssetDetailInventoryRequest;
import com.example.csvccdshustbe.service.dataDocumentInventory.DataDocumentInventoryService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataDocumentInventoryServiceImpl implements DataDocumentInventoryService {

    @Autowired
    DataDocumentInventoryRepository dataDetailInventoryRepository;

    @Override
    public List<DataDocumentInventory> createNewDataDocumentInventories(CreateInventoryAssetRequest request,
                                                                        Document document) {
        return dataDetailInventoryRepository.saveAll(contructionDataDetailInventories(request, document));
    }

    private List<DataDocumentInventory> contructionDataDetailInventories(CreateInventoryAssetRequest request, Document document) {
        List<DataDocumentInventory> dataDetailInventories = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (AssetDetailInventoryRequest dataDetail : request.getAssetDetail()){
            DataDocumentInventory detailInventory = new DataDocumentInventory();
            detailInventory.setIdAsset(dataDetail.getIdAsset());
            detailInventory.setIdDocument(document.getIdDocument());
            detailInventory.setQuantity(dataDetail.getQuantity());
            detailInventory.setOriginalValue(dataDetail.getOriginalValue());
            detailInventory.setRestValue(dataDetail.getRestValue());
            detailInventory.setStatus(Constants.STATUS_DATA_DETAIL_INVENTORY_ACTIVE);
            detailInventory.setTimeCreated(timeCurrent);
            detailInventory.setTimeModified(timeCurrent);
            dataDetailInventories.add(detailInventory);
        }
        return dataDetailInventories;
    }
}
