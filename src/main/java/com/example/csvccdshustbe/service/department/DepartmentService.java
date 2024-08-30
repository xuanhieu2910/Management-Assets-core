package com.example.csvccdshustbe.service.department;


import com.example.csvccdshustbe.request.department.FindAllDepartmentVisibleRequest;
import com.example.csvccdshustbe.response.department.FindAllDepartmentVisibleResponse;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    Page<FindAllDepartmentVisibleResponse> findAllDepartmentVisibleByCodeAndVisible(FindAllDepartmentVisibleRequest request);
}
