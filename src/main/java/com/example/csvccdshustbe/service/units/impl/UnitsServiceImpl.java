package com.example.csvccdshustbe.service.units.impl;


import com.example.csvccdshustbe.dto.unit.FindAllUnitsDto;
import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.units.UnitsRepository;
import com.example.csvccdshustbe.request.units.CreateUnitsRequest;
import com.example.csvccdshustbe.request.units.FindAllUnitsByAssetCategoryRequest;
import com.example.csvccdshustbe.request.units.UpdateUnitsRequest;
import com.example.csvccdshustbe.response.units.FindAllUnitsByCodeAssetCategoryResponse;
import com.example.csvccdshustbe.service.units.UnitsService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UnitsServiceImpl implements UnitsService {

    @Autowired
    UnitsRepository unitsRepository;


    @Override
    public List<Units> findAllUnits() {
        return unitsRepository.findAllUnits();
    }

    @Override
    public Page<FindAllUnitsByCodeAssetCategoryResponse> findAllUnitsByCodeAssetCategoryResponse(FindAllUnitsByAssetCategoryRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<Units> units = unitsRepository.findAllUnitsActiveByCodeAssetCategory(request, pageable);
        return new PageImpl<>(convertToFindAllUnitsByCodeAssetCategoryResponse(units.stream().collect(Collectors.toList())),
                pageable, units.getTotalElements());
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

    @Override
    public void createUnits(CreateUnitsRequest request) throws ValidateFiledException {
        validateDataCreateUnit(request);
        unitsRepository.save(contructUnit(request));
    }


    @Override
    public void updateUnits(UpdateUnitsRequest request) throws ValidateFiledException {
        Units units = validateDataUpdateUnit(request);
        unitsRepository.save(editUnit(units, request));
    }



    @Override
    public void deleteUnitsByIdUnits(Integer idUnit) {
        Optional<Units> unitsOptional = unitsRepository.findUnitById(idUnit);
        if (unitsOptional.isEmpty()){
            throw new NotFoundException("Don't exits Type use by id type use!");
        }
        unitsRepository.delete(unitsOptional.get());
    }

    @Override
    public Units findUnitsByIdUnitAndStatus(Integer idUnit, Integer status) {
        Optional<Units> units = unitsRepository.findUnitByIdUnitAndStatus(idUnit,  status);
        if (units.isEmpty()){
            throw new NotFoundException("Don't exits units!");
        }
        return units.get();
    }

    @Override
    public Map<String, List<FindAllUnitsDto>> findAllUnitsToDownload() {
        return unitsRepository.findAllUnitsToDownload();
    }

    private void validateDataCreateUnit(CreateUnitsRequest request) throws ValidateFiledException{
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<Units> units = unitsRepository.findUnitByName(request.getName());
        if (units.isPresent()){
            throw new ValidateFiledException("Exits Unit by name of Unit!");
        }
    }
    private Units contructUnit(CreateUnitsRequest request) {
        Units units = new Units();
        units.setName(request.getName().trim());

        if (ObjectUtils.isNotEmpty(request.getIdAssetCategory())){
            units.setIdAssetCategory(request.getIdAssetCategory());
        }
        units.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        units.setTimeCreated(timeCurrent);
        units.setTimeModified(timeCurrent);
        return units;
    }
    private Units validateDataUpdateUnit(UpdateUnitsRequest request) throws ValidateFiledException{
        Optional<Units> unitsOptional = unitsRepository.findUnitById(request.getIdUnit());
        if (unitsOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Unit by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return unitsOptional.get();
    }
    private Units editUnit(Units units, UpdateUnitsRequest request) {
        units.setName(request.getName());
        units.setStatus(request.getStatus());
        units.setIdAssetCategory(request.getIdAssetCategory());
        String timeModified = String.valueOf(new Date().getTime());
        units.setTimeModified(timeModified);
        return units;
    }


}
