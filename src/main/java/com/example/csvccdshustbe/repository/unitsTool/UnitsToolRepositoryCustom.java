package com.example.csvccdshustbe.repository.unitsTool;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.entity.UnitsTool;

import java.util.List;
import java.util.Optional;

public interface UnitsToolRepositoryCustom {
    List<UnitsTool> findAllUnitsTool();
    Optional<UnitsTool> findUnitToolByName(String name);
    Optional<UnitsTool> findUnitByIdUnitToolAndStatus(Integer idUnitTool, Integer status);
    Optional<UnitsTool> findUnitToolByIdUnitTool(Integer idUnitTool);
}
