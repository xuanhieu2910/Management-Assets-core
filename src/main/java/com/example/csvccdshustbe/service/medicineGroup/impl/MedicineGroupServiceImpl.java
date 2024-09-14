package com.example.csvccdshustbe.service.medicineGroup.impl;


import com.example.csvccdshustbe.entity.MedicineGroup;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.medicineGroup.MedicineGroupRepository;
import com.example.csvccdshustbe.request.medicineGroup.CreateMedicineGroupRequest;
import com.example.csvccdshustbe.request.medicineGroup.FindAllMedicineGroupRequest;
import com.example.csvccdshustbe.request.medicineGroup.UpdateMedicineGroupRequest;
import com.example.csvccdshustbe.response.medicineGroup.FindAllMedicineGroupResponse;
import com.example.csvccdshustbe.service.medicineGroup.MedicineGroupService;
import com.example.csvccdshustbe.utility.Constants;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineGroupServiceImpl implements MedicineGroupService {

    @Autowired
    MedicineGroupRepository medicineGroupRepository;

    @Override
    public Page<FindAllMedicineGroupResponse> findAllMedicineGroup(FindAllMedicineGroupRequest findAllMedicineGroupRequest) {
        Pageable pageable = PageUtils.buildPage(findAllMedicineGroupRequest.getPage(), findAllMedicineGroupRequest.getSize());
        Page<MedicineGroup> medicineGroups = medicineGroupRepository.findAllMedicineGroupActive(findAllMedicineGroupRequest, pageable);
        return new PageImpl<>(convertToFindAllMedicineGroupResponse(medicineGroups.stream().toList()),
                                pageable, medicineGroups.getTotalElements());
    }

    private List<FindAllMedicineGroupResponse> convertToFindAllMedicineGroupResponse(List<MedicineGroup> medicineGroups) {
        List<FindAllMedicineGroupResponse> responses = new ArrayList<>();
        for (MedicineGroup group : medicineGroups){
            FindAllMedicineGroupResponse response = new FindAllMedicineGroupResponse();
            response.setIdMedicineGroup(group.getIdMedicineGroup());
            response.setName(group.getName());
            response.setShortName(group.getShortName());
            responses.add(response);
        }
        return responses;
    }
    @Override
    public void createMedicineGroup(CreateMedicineGroupRequest request) throws ValidateFiledException {
        validateDataCreateMedicineGroup(request);
        medicineGroupRepository.save(contructMedicineGroup(request));
    }




    @Override
    public void updateMedicineGroup(UpdateMedicineGroupRequest request) throws ValidateFiledException {
        MedicineGroup medicineGroup = validateDataUpdateMedicineGroup(request);
        medicineGroupRepository.save(editMedicineGroup(medicineGroup, request));
    }




    @Override
    public void deleteMedicineGroupByIdMedicineGroup(Integer idMedicineGroup) {
        Optional<MedicineGroup> medicineGroupOptional = medicineGroupRepository.findMedicineGroupById(idMedicineGroup);
        if (medicineGroupOptional.isEmpty()){
            throw new NotFoundException("Don't exits medicine group by id!");
        }
        medicineGroupRepository.delete(medicineGroupOptional.get());
    }
    private void validateDataCreateMedicineGroup(CreateMedicineGroupRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<MedicineGroup> medicineGroup = medicineGroupRepository.findMedicineGroupByName(request.getName());
        if (medicineGroup.isPresent()){
            throw new ValidateFiledException("Exits medicine group by name!");
        }
        if (StringUtils.isNotBlank(request.getShortName())) {
            if (request.getShortName().equals(medicineGroup.get().getShortName())){
                throw new ValidateFiledException("Exits medicine group by short name");
            }
        }
    }

    private MedicineGroup contructMedicineGroup(CreateMedicineGroupRequest request) {
        MedicineGroup medicineGroup = new MedicineGroup();
        medicineGroup.setName(request.getName().trim());

        if (StringUtils.isNotBlank(request.getShortName())){
            medicineGroup.setShortName(request.getShortName());
        }
        if (StringUtils.isNotBlank(request.getDescription())){
            medicineGroup.setDescription(request.getDescription());
        }

        medicineGroup.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        medicineGroup.setTimeCreated(timeCurrent);
        medicineGroup.setTimeModified(timeCurrent);
        return medicineGroup;
    }

    private MedicineGroup validateDataUpdateMedicineGroup(UpdateMedicineGroupRequest request) throws ValidateFiledException{
        Optional<MedicineGroup> medicineGroupOptional = medicineGroupRepository.findMedicineGroupById(request.getIdMedicineGroup());
        if (medicineGroupOptional.isEmpty()) {
            throw new NotFoundException("Don't exits medicine group by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return medicineGroupOptional.get();

    }
    private MedicineGroup editMedicineGroup(MedicineGroup medicineGroup, UpdateMedicineGroupRequest request) {
        medicineGroup.setName(request.getName());
        medicineGroup.setShortName(request.getShortName());
        medicineGroup.setDescription(request.getDescription());
        medicineGroup.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        medicineGroup.setTimeModified(timeModified);
        return medicineGroup;
    }
}
