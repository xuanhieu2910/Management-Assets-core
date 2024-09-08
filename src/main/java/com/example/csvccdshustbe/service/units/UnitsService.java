package com.example.csvccdshustbe.service.units;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.units.CreateUnitsRequest;
import com.example.csvccdshustbe.request.units.FindAllUnitsByAssetCategoryRequest;
import com.example.csvccdshustbe.request.units.UpdateUnitsRequest;
import com.example.csvccdshustbe.response.units.FindAllUnitsByCodeAssetCategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UnitsService {

    List<Units> findAllUnits();

    Page<FindAllUnitsByCodeAssetCategoryResponse>
    findAllUnitsByCodeAssetCategoryResponse(FindAllUnitsByAssetCategoryRequest request);

    void createUnits(CreateUnitsRequest request) throws ValidateFiledException;

    void updateUnits(UpdateUnitsRequest request) throws ValidateFiledException;

    void deleteUnitsByIdUnits(Integer idUnit);

    Units findUnitsByIdUnitAndStatus(Integer idUnit, Integer status);
}
