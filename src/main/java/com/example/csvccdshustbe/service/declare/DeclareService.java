package com.example.csvccdshustbe.service.declare;

import com.example.csvccdshustbe.response.declare.FindAllDeclareVisibleResponse;

import java.util.List;

public interface DeclareService {

    List<FindAllDeclareVisibleResponse> findAllDeclareVisibleByIdAssetCategory(Integer idAssetCategory);

}
