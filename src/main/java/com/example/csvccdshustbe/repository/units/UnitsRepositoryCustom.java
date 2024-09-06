package com.example.csvccdshustbe.repository.units;

import com.example.csvccdshustbe.entity.Units;

import java.util.List;
import java.util.Optional;

public interface UnitsRepositoryCustom {

    List<Units> findAllUnits();
    List<Units> findAllUnitsByCodeAssetCategoryAndStatus(String codeAssetCategory,Integer status);

    Optional<Units> findUnitByName(String name);
    Optional<Units> findUnitById(Integer idUnit);

    Optional<Units> findUnitByIdUnitAndIdAssetCategoryAndStatus(Integer idUnit,Integer idAssetCategory, Integer status);
}
