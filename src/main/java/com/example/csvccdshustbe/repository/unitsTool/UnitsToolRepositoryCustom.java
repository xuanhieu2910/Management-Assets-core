package com.example.csvccdshustbe.repository.unitsTool;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.entity.UnitsTool;
import com.example.csvccdshustbe.request.units.FindAllUnitsByAssetCategoryRequest;
import com.example.csvccdshustbe.request.unitsTool.FindAllUnitsToolRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UnitsToolRepositoryCustom {
    Page<UnitsTool> findAllUnitsTool(FindAllUnitsToolRequest request, Pageable pageable);
    Optional<UnitsTool> findUnitToolByName(String name);
    Optional<UnitsTool> findUnitByIdUnitToolAndStatus(Integer idUnitTool, Integer status);
    Optional<UnitsTool> findUnitToolByIdUnitTool(Integer idUnitTool);
}
