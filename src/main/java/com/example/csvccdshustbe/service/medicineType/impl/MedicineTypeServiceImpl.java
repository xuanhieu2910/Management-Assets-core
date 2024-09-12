package com.example.csvccdshustbe.service.medicineType.impl;

import com.example.csvccdshustbe.dto.modules.medicineModules.FindAllMedicineTypeDto;
import com.example.csvccdshustbe.entity.MedicineType;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.medicineType.MedicineTypeRepository;

import com.example.csvccdshustbe.request.medicineType.CreateMedicineTypeRequest;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeRequest;
import com.example.csvccdshustbe.request.medicineType.UpdateMedicineTypeRequest;
import com.example.csvccdshustbe.response.medicineType.FindAllMedicineTypeResponse;
import com.example.csvccdshustbe.service.medicineType.MedicineTypeService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicineTypeServiceImpl implements MedicineTypeService {

    @Autowired
    MedicineTypeRepository medicineTypeRepository;

    @Override
    public Page<FindAllMedicineTypeResponse> findAllMedicineTypeResponse(FindAllMedicineTypeRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllMedicineTypeDto> findAllMedicineTypeDtos = medicineTypeRepository.findAllMedicineTypeVisible(request, pageable);
        return new PageImpl<>(convertToFindAllMedicineTypeResponse(findAllMedicineTypeDtos.get().collect(Collectors.toList())),
                pageable, findAllMedicineTypeDtos.getTotalElements());
    }

    private List<FindAllMedicineTypeResponse> convertToFindAllMedicineTypeResponse(List<FindAllMedicineTypeDto> collect) {
        List<FindAllMedicineTypeResponse> responses = new ArrayList<>();
        for (FindAllMedicineTypeDto dto : collect) {
            FindAllMedicineTypeResponse response = new FindAllMedicineTypeResponse();
            response.setIdMedicineType(dto.getIdMedicineType());
            response.setName(dto.getName());
            response.setCode(dto.getCode());
            response.setParent(dto.getParent());
            response.setDepth(dto.getDepth());
            response.setPath(dto.getPath());
            responses.add(response);
        }
        return responses;
    }


    @Override
    public void createMedicineType(CreateMedicineTypeRequest request) throws ValidateFiledException {
        validateDataCreateMedicineType(request);
        medicineTypeRepository.save(contructMedicineType(request));
    }


    @Override
    public void updateMedicineType(UpdateMedicineTypeRequest request) throws ValidateFiledException {
        MedicineType medicineType = validateDataUpdateMedicineType(request);
        medicineTypeRepository.save(editMedicineType(medicineType, request));
    }


    @Override
    public void deleteMedicineTypeByIdMedicineType(Integer idMedicineType) {
        Optional<MedicineType> medicineTypeOptional = medicineTypeRepository.findMedicineTypeById(idMedicineType);
        if (!medicineTypeOptional.isPresent()) {
            throw new NotFoundException("Don't exits Medicine Type by id!");
        }
        medicineTypeRepository.delete(medicineTypeOptional.get());
    }

    private void validateDataCreateMedicineType(CreateMedicineTypeRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<MedicineType> medicineType = medicineTypeRepository.findMedicineTypeByName(request.getName());
        if (medicineType.isPresent()) {
            throw new ValidateFiledException("Exits medicine type by name medicine type!");
        }
        if (StringUtils.isNotBlank(request.getShortName())) {
            if (request.getShortName().equals(medicineType.get().getShortName())) {
                throw new ValidateFiledException("Exits medicine type by short name");
            }
        }
        if (StringUtils.isNotBlank(request.getCode())) {
            if (request.getCode().equals(medicineType.get().getCode())) {
                throw new ValidateFiledException("Exits medicine type by code name");
            }
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            Optional<MedicineType> medicineTypeOptional = medicineTypeRepository.findMedicineTypeByIdParent(request.getParentId());
            if (!medicineTypeOptional.isPresent()) {
                throw new ValidateFiledException("Don't exits medicine type by id parent!");
            }
        }
    }

    private MedicineType contructMedicineType(CreateMedicineTypeRequest request) {
        MedicineType medicineType = new MedicineType();
        medicineType.setName(request.getName().trim());
        if (StringUtils.isNotBlank(request.getCode())) {
            medicineType.setCode(request.getCode());
        }
        if (StringUtils.isNotBlank(request.getShortName())) {
            medicineType.setShortName(request.getShortName());
        }
        if (StringUtils.isNotBlank(request.getNotes())) {
            medicineType.setNotes(request.getNotes());
        }
        if (ObjectUtils.isNotEmpty(request.getParentId())) {
            medicineType.setParent(request.getParentId());
        }
        if (ObjectUtils.isNotEmpty(request.getVisible())) {
            medicineType.setVisible(request.getVisible());
        }

        String timeCurrent = String.valueOf(new Date().getTime());
        medicineType.setTimeCreated(timeCurrent);
        medicineType.setTimeModified(timeCurrent);
        return medicineType;
    }

    private MedicineType validateDataUpdateMedicineType(UpdateMedicineTypeRequest request) throws ValidateFiledException {
        Optional<MedicineType> medicineTypeOptional = medicineTypeRepository.findMedicineTypeById(request.getIdMedicineType());
        if (!medicineTypeOptional.isPresent()) {
            throw new NotFoundException("Don't exits Medicine Type by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        if (!medicineTypeOptional.get().getName().equals(request.getName()) ||
                !medicineTypeOptional.get().getCode().equals(request.getCode()) ||
                !medicineTypeOptional.get().getShortName().equals(request.getShortName())) {
            if (medicineTypeRepository.checkExitsMedicineTypeByNameOrCodeOrShortName(request.getName(),
                    request.getCode(), request.getShortName())) {
                throw new ValidateFiledException("Exits medicine type by name or code or short name!");
            }
        }
        return medicineTypeOptional.get();

    }

    private MedicineType editMedicineType(MedicineType medicineType, UpdateMedicineTypeRequest request) {
        medicineType.setName(request.getName());
        medicineType.setCode(request.getCode());
        medicineType.setShortName(request.getShortName());
        medicineType.setNotes(request.getNotes());
        medicineType.setParent(request.getParentId());
        medicineType.setVisible(request.getVisible());
        String timeModified = String.valueOf(new Date().getTime());
        medicineType.setTimeModified(timeModified);
        return medicineType;
    }
}
