package com.example.csvccdshustbe.service.medicineType.impl;

import com.example.csvccdshustbe.dto.medicine.FindAllMedicineTypeDto;
import com.example.csvccdshustbe.repository.medicineType.MedicineTypeRepository;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeRequest;
import com.example.csvccdshustbe.response.medicineType.FindAllMedicineTypeResponse;
import com.example.csvccdshustbe.service.medicineType.MedicineTypeService;
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
}
