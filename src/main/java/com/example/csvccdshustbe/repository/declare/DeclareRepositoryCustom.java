package com.example.csvccdshustbe.repository.declare;

import com.example.csvccdshustbe.entity.Declare;

import java.util.List;
import java.util.Optional;

public interface DeclareRepositoryCustom {

    List<Declare> findAllDeclareByIdAssetCategoryAndVisible(Integer idAssetCategory, Integer visible);
    Optional<Declare> findDeclareByHardCodeAndVisible(String hardCode, Integer visible);
}
