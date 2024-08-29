package com.example.csvccdshustbe.service.department.impl;

import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.repository.department.DepartmentRepository;
import com.example.csvccdshustbe.request.department.FindAllDepartmentRequest;

import com.example.csvccdshustbe.response.department.FindAllDepartmentResponse;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Page<FindAllDepartmentResponse> findAllDepartmentByCodeAndVisible(
            FindAllDepartmentRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllDepartmentByCodeAndVisibleDto> department =
                departmentRepository.findAllDepartmentByCodeAndVisible(pageable, request);
        return new PageImpl<>(convertToFindAllDepartmentByCodeAndVisible(department.get().collect(Collectors.toList())),
                pageable, department.getTotalElements());
    }


    private List<FindAllDepartmentResponse>convertToFindAllDepartmentByCodeAndVisible
            (List<FindAllDepartmentByCodeAndVisibleDto> collect){
        List<FindAllDepartmentResponse> responses = new ArrayList<>();
        for (FindAllDepartmentByCodeAndVisibleDto department : collect){
            FindAllDepartmentResponse response= new FindAllDepartmentResponse();
            response.setIdDepartment(department.getIdDepartment());
            response.setName(department.getName());
            response.setCode(department.getCode());
            response.setDepth(department.getDepth());
            response.setStatus(department.getStatus());
            response.setParent(department.getParent());
            response.setPath(department.getPath());
            responses.add(response);
        }
        return responses;
    }
}
