package com.example.csvccdshustbe.repository.declare;

import com.example.csvccdshustbe.entity.Declare;

import java.util.List;

public interface DeclareRepositoryCustom {

    List<Declare> findAllDeclareByIdAssetCategoryAndVisible(Integer idAssetCategory, Integer visible);
}
