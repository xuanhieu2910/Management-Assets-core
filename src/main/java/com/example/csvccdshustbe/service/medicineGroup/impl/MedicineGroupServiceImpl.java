package com.example.csvccdshustbe.service.medicineGroup.impl;

import com.example.csvccdshustbe.entity.MedicineGroup;
import com.example.csvccdshustbe.repository.medicineGroup.MedicineGroupRepository;
import com.example.csvccdshustbe.response.medicineGroup.FindAllMedicineGroupResponse;
import com.example.csvccdshustbe.service.medicineGroup.MedicineGroupService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineGroupServiceImpl implements MedicineGroupService {

    @Autowired
    MedicineGroupRepository medicineGroupRepository;

    @Override
    public List<FindAllMedicineGroupResponse> findAllMedicineGroupByStatus(Integer status) {
        List<MedicineGroup> medicineGroups = medicineGroupRepository.findAllMedicineGroupByStatus(status);
        return convertToFindAllMedicineGroupResponse(medicineGroups);
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
}
