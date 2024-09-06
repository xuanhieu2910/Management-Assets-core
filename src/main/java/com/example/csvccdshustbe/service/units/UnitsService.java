package com.example.csvccdshustbe.service.units;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.units.CreateUnitsRequest;
import com.example.csvccdshustbe.request.units.UpdateUnitsRequest;
import com.example.csvccdshustbe.response.units.FindAllUnitsByCodeAssetCategoryResponse;

import java.util.List;

public interface UnitsService {

    List<Units> findAllUnits();

    List<FindAllUnitsByCodeAssetCategoryResponse> findAllUnitsByCodeAssetCategoryResponse(String codeName);

    void createUnits(CreateUnitsRequest request) throws ValidateFiledException;

    void updateUnits(UpdateUnitsRequest request) throws ValidateFiledException;

    void deleteUnitsByIdUnits(Integer idUnit);
}
