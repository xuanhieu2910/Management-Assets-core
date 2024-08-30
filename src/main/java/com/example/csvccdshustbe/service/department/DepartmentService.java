package com.example.csvccdshustbe.service.department;


import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.department.CreateDepartmentRequest;
import com.example.csvccdshustbe.request.department.FindAllDepartmentRequest;
import com.example.csvccdshustbe.request.department.FindAllDepartmentSRequest;
import com.example.csvccdshustbe.request.department.UpdateDepartmentRequest;
import com.example.csvccdshustbe.response.department.FindAllDepartmentResponse;
import com.example.csvccdshustbe.response.department.FindAllDepartmentSResponse;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    Page<FindAllDepartmentResponse>findAllDepartmentByCodeAndVisible(FindAllDepartmentRequest request);

    Page<FindAllDepartmentSResponse> findAllDepartment(FindAllDepartmentSRequest request);

    void createDepartment(CreateDepartmentRequest request) throws ValidateFiledException;

    void updateDepartment(UpdateDepartmentRequest request) throws ValidateFiledException;

    void deleteDepartmentByIdDepartment(Integer idDepartment);
}
