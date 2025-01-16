package com.example.csvccdshustbe.service.unitsTool;


import com.example.csvccdshustbe.entity.UnitsTool;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.unitsTool.CreateUnitsToolRequest;
import com.example.csvccdshustbe.request.unitsTool.FindAllUnitsToolRequest;
import com.example.csvccdshustbe.request.unitsTool.UpdateUnitsToolRequest;
import org.springframework.data.domain.Page;


public interface UnitsToolService {
    Page<UnitsTool> findAllUnitsTool(FindAllUnitsToolRequest request);
    void createUnitsTool(CreateUnitsToolRequest request) throws ValidateFiledException;
    void updateUnitsTool(UpdateUnitsToolRequest request) throws ValidateFiledException;
    void deleteUnitsToolByIdUnitsTool(Integer idUnitTool);
    UnitsTool findUnitsByIdUnitToolAndStatus(Integer idUnitTool, Integer status);
}
