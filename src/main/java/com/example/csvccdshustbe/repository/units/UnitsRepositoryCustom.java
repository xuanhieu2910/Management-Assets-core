package com.example.csvccdshustbe.repository.units;

import com.example.csvccdshustbe.entity.Units;

import java.util.List;

public interface UnitsRepositoryCustom {

    List<Units> findAllUnits();
    List<Units> findAllUnitsByCodeAssetCategoryAndStatus(String codeAssetCategory,Integer status);
}
