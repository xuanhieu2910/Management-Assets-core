package com.example.csvccdshustbe.service.department;


import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.dto.location.FindAllLocationDto;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.department.*;
import com.example.csvccdshustbe.response.department.FindAllDepartmentSResponse;
import com.example.csvccdshustbe.response.department.FindAllDepartmentVisibleResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    Page<FindAllDepartmentSResponse> findAllDepartment(FindAllDepartmentRequest request);
    void createDepartment(CreateDepartmentRequest request) throws ValidateFiledException;
    void updateDepartment(UpdateDepartmentRequest request) throws ValidateFiledException;
    void deleteDepartmentByIdDepartment(Integer idDepartment) throws ValidateFiledException;
    Page<FindAllDepartmentVisibleResponse> findAllDepartmentVisibleByCodeAndVisible(FindAllDepartmentVisibleRequest request);
    List<FindAllDepartmentByCodeAndVisibleDto> findAllDepartmentVisibleByCodeAndVisible();
    Map<String,List<FindAllLocationDto>> findAllDepartmentLocationVisibleToDownload();
    Department findDepartmentByIdDepartmentAndStatus(Integer idDepartment, Integer status);
    void updateStatusDepartment(UpdateStatusDepartmentRequest request) throws ValidateFiledException;
    List<Department> findDepartmentByIds(List<Integer> ids) throws ValidateFiledException;
    List<FindAllDepartmentByCodeAndVisibleDto> findAllStructureDepartmentByIdDepartment(Integer department);
    List<Integer> findIdsStructureDepartment(Integer department);
}
