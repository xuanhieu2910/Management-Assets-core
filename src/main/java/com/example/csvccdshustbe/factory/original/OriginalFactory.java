package com.example.csvccdshustbe.factory.original;

import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import com.example.csvccdshustbe.entity.IOriginal;

import java.util.Map;

public interface OriginalFactory {

    IOriginal createOriginal(Map<String, Object> mapOriginalCreate);
    IOriginal copyOriginal(AssetOriginalDto assetOriginalDtoRoot, Integer idAsset);

    IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails);
}
