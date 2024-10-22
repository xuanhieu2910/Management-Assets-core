package com.example.csvccdshustbe.repository.dataProcessAsset;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetDto;
import com.example.csvccdshustbe.entity.DataProcessAsset;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DataProcessAssetRepositoryCustom {
    List<DataProcessAsset> findDataProcessIncreaseAssetByIdsAsset(List<Integer> idsAsset);
    Page<FindAllProcessAssetDto> findAllProcessAssetDtoByIdsDepartment(FindAllProcessAssetRequest request, Pageable pageable);

}
