package com.example.csvccdshustbe.service.department;


import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.department.*;
import com.example.csvccdshustbe.response.department.FindAllDepartmentSResponse;
import com.example.csvccdshustbe.response.department.FindAllDepartmentVisibleResponse;
import org.springframework.data.domain.Page;

public interface DepartmentService {

    Page<FindAllDepartmentSResponse> findAllDepartment(FindAllDepartmentRequest request);

    void createDepartment(CreateDepartmentRequest request) throws ValidateFiledException;

    void updateDepartment(UpdateDepartmentRequest request) throws ValidateFiledException;

    void deleteDepartmentByIdDepartment(Integer idDepartment) throws ValidateFiledException;
    Page<FindAllDepartmentVisibleResponse> findAllDepartmentVisibleByCodeAndVisible(FindAllDepartmentVisibleRequest request);

    Department findDepartmentByIdDepartmentAndStatus(Integer idDepartment, Integer status);

    void updateStatusDepartment(UpdateStatusDepartmentRequest request) throws ValidateFiledException;
}
