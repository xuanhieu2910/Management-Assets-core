package com.example.csvccdshustbe.service.department;


import com.example.csvccdshustbe.request.department.FindAllDepartmentRequest;
import com.example.csvccdshustbe.response.department.FindAllDepartmentResponse;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    Page<FindAllDepartmentResponse>findAllDepartmentByCodeAndVisible(FindAllDepartmentRequest request);
}
