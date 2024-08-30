package com.example.csvccdshustbe.repository.department;

import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.request.department.FindAllDepartmentVisibleRequest;
import com.example.csvccdshustbe.dto.department.FindAllDepartmentSDto;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.request.department.FindAllDepartmentRequest;
import com.example.csvccdshustbe.request.department.FindAllDepartmentSRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DepartmentRepositoryCustom {
    Page<FindAllDepartmentByCodeAndVisibleDto>
    findAllDepartmentByCodeAndVisible(Pageable pageable, FindAllDepartmentVisibleRequest request);
    findAllDepartmentByCodeAndVisible(Pageable pageable, FindAllDepartmentRequest request);

    Page<FindAllDepartmentSDto> findAllDepartment(Pageable pageable, FindAllDepartmentSRequest request);

    Optional<Department> findDepartmentByName(String name);

    Optional<Department> findDepartmentByIdParent(Integer idParent);

    Optional<Department> findDepartmentById(Integer idDepartment);

    boolean checkExitsDepartmentByNameOrCodeOrShortName(String name, String code, String shortName);
}
