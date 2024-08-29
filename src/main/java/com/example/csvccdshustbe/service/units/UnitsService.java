package com.example.csvccdshustbe.service.units;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.response.units.FindAllUnitsByCodeAssetCategoryResponse;

import java.util.List;

public interface UnitsService {

    List<Units> findAllUnits();

    List<FindAllUnitsByCodeAssetCategoryResponse> findAllUnitsByCodeAssetCategoryResponse(String codeName);
}
