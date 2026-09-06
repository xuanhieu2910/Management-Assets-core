package com.example.csvccdshustbe.service.declare;

import com.example.csvccdshustbe.entity.Declare;
import com.example.csvccdshustbe.response.declare.FindAllDeclareVisibleResponse;

import java.util.List;

public interface DeclareService {

    List<FindAllDeclareVisibleResponse> findAllDeclareVisibleByIdAssetCategory(Integer idAssetCategory);

    Declare findDeclareByHardCodeAndVisible(String hardCode, Integer visible);

}
