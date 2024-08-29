package com.example.csvccdshustbe.repository.department;

import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.request.department.FindAllDepartmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentRepositoryCustom {
    Page<FindAllDepartmentByCodeAndVisibleDto>
    findAllDepartmentByCodeAndVisible(Pageable pageable, FindAllDepartmentRequest request);
}
