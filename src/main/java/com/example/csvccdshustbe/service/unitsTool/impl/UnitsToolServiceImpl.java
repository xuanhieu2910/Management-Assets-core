package com.example.csvccdshustbe.service.unitsTool.impl;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.entity.UnitsTool;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.unitsTool.UnitsToolRepository;
import com.example.csvccdshustbe.request.unitsTool.CreateUnitsToolRequest;
import com.example.csvccdshustbe.request.unitsTool.FindAllUnitsToolRequest;
import com.example.csvccdshustbe.request.unitsTool.UpdateUnitsToolRequest;
import com.example.csvccdshustbe.service.unitsTool.UnitsToolService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitsToolServiceImpl implements UnitsToolService {
    @Autowired
    UnitsToolRepository unitsToolRepository;

    @Override
    public Page<UnitsTool> findAllUnitsTool(FindAllUnitsToolRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<UnitsTool> unitsTool = unitsToolRepository.findAllUnitsTool(request, pageable);
        return new PageImpl<>(convertToFindAllUnitsToolsResponse(unitsTool.stream().collect(Collectors.toList())),
                pageable, unitsTool.getTotalElements());
    }

    private List<UnitsTool> convertToFindAllUnitsToolsResponse(List<UnitsTool> collect) {
        List<UnitsTool> unitsTools = new ArrayList<>();
        for (UnitsTool unitsTool : collect) {
            UnitsTool tempUnitsTool = new UnitsTool();
            tempUnitsTool.setIdUnitTool(unitsTool.getIdUnitTool());
            tempUnitsTool.setName(unitsTool.getName());
            tempUnitsTool.setStatus(unitsTool.getStatus());
            unitsTools.add(tempUnitsTool);
        }
        return unitsTools;
    }

    @Override
    public void createUnitsTool(CreateUnitsToolRequest request) throws ValidateFiledException {
        validateDataCreateUnitTool(request);
        unitsToolRepository.save(constructUnitTool(request));
    }

    @Override
    public void updateUnitsTool(UpdateUnitsToolRequest request) throws ValidateFiledException {
        UnitsTool unitsTool = validateDataUpdateUnitTool(request);
        unitsToolRepository.save(editUnitTool(unitsTool, request));
    }

    private UnitsTool editUnitTool(UnitsTool unitsTool, UpdateUnitsToolRequest request) {
        unitsTool.setName(request.getName());
        unitsTool.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        unitsTool.setTimeModified(timeModified);
        return unitsTool;
    }

    private UnitsTool validateDataUpdateUnitTool(UpdateUnitsToolRequest request) throws ValidateFiledException {
        Optional<UnitsTool> unitsOptional = unitsToolRepository.findUnitToolByIdUnitTool(request.getIdUnitTool());
        if (unitsOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Unit by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return unitsOptional.get();
    }

    @Override
    public void deleteUnitsToolByIdUnitsTool(Integer idUnitTool) {
        Optional<UnitsTool> unitsTool = unitsToolRepository.findUnitToolByIdUnitTool(idUnitTool);
        if (unitsTool.isEmpty()){
            throw new NotFoundException("Don't exits unit tool by id !");
        }
        unitsToolRepository.delete(unitsTool.get());
    }

    @Override
    public UnitsTool findUnitsByIdUnitToolAndStatus(Integer idUnitTool, Integer status) {
        Optional<UnitsTool> unitsTool = unitsToolRepository.findUnitByIdUnitToolAndStatus(idUnitTool,  status);
        if (unitsTool.isEmpty()){
            throw new NotFoundException("Don't exits units tool!");
        }
        return unitsTool.get();
    }

    private UnitsTool constructUnitTool(CreateUnitsToolRequest request) {
        UnitsTool unitsTool = new UnitsTool();
        unitsTool.setName(request.getName().trim());
        unitsTool.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        unitsTool.setTimeCreated(timeCurrent);
        unitsTool.setTimeModified(timeCurrent);
        return unitsTool;
    }

    private void validateDataCreateUnitTool(CreateUnitsToolRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<UnitsTool> unitsTool = unitsToolRepository.findUnitToolByName(request.getName());
        if (unitsTool.isPresent()){
            throw new ValidateFiledException("Exits Unit tool by name !");
        }
    }
}
