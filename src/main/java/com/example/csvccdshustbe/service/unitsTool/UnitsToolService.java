package com.example.csvccdshustbe.service.unitsTool;


import com.example.csvccdshustbe.entity.UnitsTool;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.unitsTool.CreateUnitsToolRequest;
import com.example.csvccdshustbe.request.unitsTool.UpdateUnitsToolRequest;


import java.util.List;

public interface UnitsToolService {
    List<UnitsTool> findAllUnitsTool();
    void createUnitsTool(CreateUnitsToolRequest request) throws ValidateFiledException;
    void updateUnitsTool(UpdateUnitsToolRequest request) throws ValidateFiledException;
    void deleteUnitsToolByIdUnitsTool(Integer idUnitTool);
    UnitsTool findUnitsByIdUnitToolAndStatus(Integer idUnitTool, Integer status);
}
