package com.example.csvccdshustbe.repository.department;

import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.request.department.FindAllDepartmentVisibleRequest;
import com.example.csvccdshustbe.dto.department.FindAllDepartmentSDto;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.request.department.FindAllDepartmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepositoryCustom {
    Page<FindAllDepartmentByCodeAndVisibleDto>
    findAllDepartmentByCodeAndVisible(Pageable pageable, FindAllDepartmentVisibleRequest request);
  List<FindAllDepartmentByCodeAndVisibleDto> findAllDepartmentByCodeAndVisible();

    Page<FindAllDepartmentSDto> findAllDepartment(Pageable pageable, FindAllDepartmentRequest request);

    Optional<Department> findDepartmentByName(String name);

    Optional<Department> findDepartmentByIdParent(Integer idParent);

    Optional<Department> findDepartmentById(Integer idDepartment);

    Optional<Department> findDepartmentByIdDepartmentAndStatus(Integer idDepartment, Integer status);

    boolean checkExitsDepartmentByNameOrCodeOrShortName(String name, String code, String shortName);

    boolean isExitsAssetByIdDepartment(Integer idDepartment);

}
