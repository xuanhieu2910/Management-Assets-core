package com.example.csvccdshustbe.factory.original;

import com.example.csvccdshustbe.entity.IOriginal;

import java.util.Map;

public interface OriginalFactory {

    IOriginal createOriginal(Map<String, Object> mapOriginalCreate);

    IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails);
}
