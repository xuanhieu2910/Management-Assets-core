package com.example.csvccdshustbe.repository.dataDocument;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetIncreaseDto;
import com.example.csvccdshustbe.dto.process.FindAllProcessAssetInventoryDto;
import com.example.csvccdshustbe.entity.DataDocument;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetIncreaseRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetInventoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DataDocumentRepositoryCustom {
    List<DataDocument> findDataProcessIncreaseAssetByIdsAsset(List<Integer> idsAsset);
    Page<FindAllProcessAssetIncreaseDto> findAllProcessAssetIncreaseDtoByIdsDepartment(FindAllProcessAssetIncreaseRequest request,
                                                                                       Pageable pageable);

    Page<FindAllProcessAssetInventoryDto> findAllProcessAssetInventoryDtoByIdsDepartment(FindAllProcessAssetInventoryRequest request,
                                                                                         Pageable pageable);

}
