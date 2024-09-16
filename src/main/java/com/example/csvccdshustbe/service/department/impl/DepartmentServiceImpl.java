package com.example.csvccdshustbe.service.department.impl;

import com.example.csvccdshustbe.dto.department.FindAllDepartmentByCodeAndVisibleDto;
import com.example.csvccdshustbe.dto.department.FindAllDepartmentSDto;
import com.example.csvccdshustbe.entity.Department;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.asset.AssetRepository;
import com.example.csvccdshustbe.repository.department.DepartmentRepository;
import com.example.csvccdshustbe.request.department.*;
import com.example.csvccdshustbe.response.department.FindAllDepartmentSResponse;
import com.example.csvccdshustbe.response.department.FindAllDepartmentVisibleResponse;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;



    @Override
    public Page<FindAllDepartmentVisibleResponse> findAllDepartmentVisibleByCodeAndVisible(
            FindAllDepartmentVisibleRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllDepartmentByCodeAndVisibleDto> department =
                departmentRepository.findAllDepartmentByCodeAndVisible(pageable, request);
        return new PageImpl<>(convertToFindAllDepartmentVisibleByCodeAndVisible(department.get().collect(Collectors.toList())),
                pageable, department.getTotalElements());
    }

    @Override
    public Department findDepartmentByIdDepartmentAndStatus(Integer idDepartment, Integer status) {
        Optional<Department> departmentOptional = departmentRepository.findDepartmentByIdDepartmentAndStatus(idDepartment, status);
        if (departmentOptional.isEmpty()){
            throw new NotFoundException("Don't exits department by id and status");
        }
        return departmentOptional.get();
    }

    @Override
    public void updateStatusDepartment(UpdateStatusDepartmentRequest request) throws ValidateFiledException {
        Optional<Department> departmentOptional = departmentRepository.findDepartmentById(request.getIdDepartment());
        if (departmentOptional.isEmpty()){
            throw new NotFoundException("Don't exits department by id!");
        }
        if (!request.getStatus().equals(Constants.DEPARTMENT_ACTIVE_STATUS) ||
                !request.getStatus().equals(Constants.DEPARTMENT_UN_ACTIVE_STATUS)) {
            throw new ValidateFiledException("Don't exits status department!");
        }
        departmentOptional.get().setStatus(request.getStatus());
    }

    @Override
    public Page<FindAllDepartmentSResponse> findAllDepartment(FindAllDepartmentRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllDepartmentSDto> dtos = departmentRepository.findAllDepartment(pageable, request);
        return new PageImpl<>(convertToFindAllDepartment(dtos.stream().collect(Collectors.toList())),
                pageable, dtos.getTotalElements());
    }

    @Override
    public void createDepartment(CreateDepartmentRequest request) throws ValidateFiledException {
        validateDataCreateDepartment(request);
        departmentRepository.save(contructDepartment(request));
    }

    @Override
    public void updateDepartment(UpdateDepartmentRequest request) throws ValidateFiledException {
        Department department = validateDataUpdateDepartment(request);
        departmentRepository.save(editDepartment(department, request));
    }

    @Override
    public void deleteDepartmentByIdDepartment(Integer idDepartment) throws ValidateFiledException {
        Optional<Department> departmentOptional = departmentRepository.findDepartmentById(idDepartment);
        if (departmentOptional.isEmpty()){
            throw new NotFoundException("Don't exits department by id department!");
        }
        if (departmentRepository.isExitsAssetByIdDepartment(idDepartment)){
            throw new ValidateFiledException("Exits asset, can't delete department!");
        }
        departmentRepository.delete(departmentOptional.get());
    }

    private Department editDepartment(Department department, UpdateDepartmentRequest request) {
        department.setName(request.getName());
        department.setCode(request.getCode());
        department.setShortName(request.getShortName());
        department.setDescription(request.getDescription());
        department.setParent(request.getParentId());
        department.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        department.setTimeModified(timeModified);
        return department;
    }

    private Department validateDataUpdateDepartment(UpdateDepartmentRequest request) throws ValidateFiledException {
        Optional<Department> departmentOptional = departmentRepository.findDepartmentById(request.getIdDepartment());
        if (departmentOptional.isEmpty()) {
            throw new NotFoundException("Don't exits department by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!departmentOptional.get().getName().equals(request.getName()) ||
            !departmentOptional.get().getCode().equals(request.getCode()) ||
            !departmentOptional.get().getShortName().equals(request.getShortName())) {
            if (departmentRepository.checkExitsDepartmentByNameOrCodeOrShortName(request.getName(),
                    request.getCode(), request.getShortName())) {
                throw new ValidateFiledException("Exits department by name or code or short name!");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<Department> departmentByIdParent = departmentRepository.findDepartmentByIdParent(request.getParentId());
            if (departmentByIdParent.isEmpty()){
                throw new ValidateFiledException("Don't exits department by id parent!");
            }
        }
        return departmentOptional.get();
    }

    private Department contructDepartment(CreateDepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName().trim());
        if (StringUtils.isNotBlank(request.getCode())) {
            department.setCode(request.getCode());
        }
        if (StringUtils.isNotBlank(request.getShortName())){
            department.setShortName(request.getShortName());
        }
        if (StringUtils.isNotBlank(request.getDescription())){
            department.setDescription(request.getDescription());
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())){
            department.setParent(request.getParentId());
        }
        department.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        department.setTimeCreated(timeCurrent);
        department.setTimeModified(timeCurrent);
        return department;
    }

    private void validateDataCreateDepartment(CreateDepartmentRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<Department> department = departmentRepository.findDepartmentByName(request.getName());
        if (department.isPresent()){
            throw new ValidateFiledException("Exits department by name department!");
        }
        if (StringUtils.isNotBlank(request.getShortName())) {
            if (request.getShortName().equals(department.get().getShortName())){
                throw new ValidateFiledException("Exits department by short name");
            }
        }
        if (StringUtils.isNotBlank(request.getCode())) {
            if (request.getCode().equals(department.get().getCode())){
                throw new ValidateFiledException("Exits department by code name");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<Department> departmentOptional = departmentRepository.findDepartmentByIdParent(request.getParentId());
            if (departmentOptional.isEmpty()){
                throw new ValidateFiledException("Don't exits department by id parent!");
            }
        }
    }

    private List<FindAllDepartmentSResponse> convertToFindAllDepartment(List<FindAllDepartmentSDto> collect) {
        List<FindAllDepartmentSResponse> responses = new ArrayList<>();
        for (FindAllDepartmentSDto dto : collect){
            FindAllDepartmentSResponse response = new FindAllDepartmentSResponse();
            response.setIdDepartment(dto.getIdDepartment());
            response.setName(dto.getName());
            response.setCode(dto.getCode());
            response.setShortName(dto.getShortName());
            response.setDescription(dto.getDescription());
            response.setParent(dto.getParent());
            Date timeCreated = DateUtil.formatDatePattern(dto.getTimeCreated(), DateUtil.DDMMYYYY);
            Date timeModified = DateUtil.formatDatePattern(dto.getTimeModified(), DateUtil.DDMMYYYY);
            response.setTimeCreated(DateUtil.formatToPattern(timeCreated, DateUtil.DDMMYYYY));
            response.setTimeModified(DateUtil.formatToPattern(timeModified, DateUtil.DDMMYYYY));
            response.setDepth(dto.getDepth());
            response.setPath(dto.getPath());
            responses.add(response);
        }
        return responses;
    }


    private List<FindAllDepartmentVisibleResponse>convertToFindAllDepartmentVisibleByCodeAndVisible
            (List<FindAllDepartmentByCodeAndVisibleDto> collect){
        List<FindAllDepartmentVisibleResponse> responses = new ArrayList<>();
        for (FindAllDepartmentByCodeAndVisibleDto department : collect){
            FindAllDepartmentVisibleResponse response= new FindAllDepartmentVisibleResponse();
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
