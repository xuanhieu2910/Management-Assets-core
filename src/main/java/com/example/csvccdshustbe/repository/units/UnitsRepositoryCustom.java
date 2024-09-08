package com.example.csvccdshustbe.repository.units;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.request.units.FindAllUnitsByAssetCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UnitsRepositoryCustom {

    List<Units> findAllUnits();
    Page<Units> findAllUnitsActiveByCodeAssetCategory(FindAllUnitsByAssetCategoryRequest request, Pageable pageable);

    Optional<Units> findUnitByName(String name);
    Optional<Units> findUnitById(Integer idUnit);

    Optional<Units> findUnitByIdUnitAndIdAssetCategoryAndStatus(Integer idUnit,Integer idAssetCategory, Integer status);
}
