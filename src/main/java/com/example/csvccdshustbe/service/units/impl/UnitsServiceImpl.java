package com.example.csvccdshustbe.service.units.impl;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.repository.units.UnitsRepository;
import com.example.csvccdshustbe.response.units.FindAllUnitsByCodeAssetCategoryResponse;
import com.example.csvccdshustbe.service.units.UnitsService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnitsServiceImpl implements UnitsService {

    @Autowired
    UnitsRepository unitsRepository;


    @Override
    public List<Units> findAllUnits() {
        return unitsRepository.findAllUnits();
    }

    @Override
    public List<FindAllUnitsByCodeAssetCategoryResponse> findAllUnitsByCodeAssetCategoryResponse(String codeName) {
        List<Units> units = unitsRepository.findAllUnitsByCodeAssetCategoryAndStatus(codeName, Constants.UNITS_IS_ACTIVE);
        return convertToFindAllUnitsByCodeAssetCategoryResponse(units);
    }

    private List<FindAllUnitsByCodeAssetCategoryResponse>
    convertToFindAllUnitsByCodeAssetCategoryResponse(List<Units> units) {
        List<FindAllUnitsByCodeAssetCategoryResponse> responses = new ArrayList<>();
        for (Units unit: units) {
            FindAllUnitsByCodeAssetCategoryResponse response = new FindAllUnitsByCodeAssetCategoryResponse();
            response.setIdUnit(unit.getIdUnit());
            response.setName(unit.getName());
            responses.add(response);
        }
        return responses;
    }
}
