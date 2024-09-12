package com.example.csvccdshustbe.service.modules.medicineModule.impl;

import com.example.csvccdshustbe.dto.modules.medicineModules.MedicineModuleDetailsDto;
import com.example.csvccdshustbe.entity.MedicineModule;
import com.example.csvccdshustbe.repository.medicineModule.MedicineModuleRepository;
import com.example.csvccdshustbe.service.medicineGroup.MedicineGroupService;
import com.example.csvccdshustbe.service.medicineType.MedicineTypeService;
import com.example.csvccdshustbe.service.modules.medicineModule.MedicineModuleService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class MedicineModuleServiceImpl implements MedicineModuleService {

    @Autowired
    MedicineModuleRepository medicineModuleRepository;

    @Autowired
    MedicineGroupService medicineGroupService;
    @Autowired
    MedicineTypeService medicineTypeService;

    @Override
    public MedicineModule save(MedicineModule medicineModule) {
        return medicineModuleRepository.save(medicineModule);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public Map<String, Object> findMedicineModuleDetailsByIdMedicine(Integer idMedicine) throws IllegalAccessException {
        Optional<MedicineModuleDetailsDto> medicineModule = medicineModuleRepository.findMedicineModuleDetailsDtoById(idMedicine);
        if (!medicineModule.isPresent()) {
            throw new NotFoundException("Don't exits medicine modules!");
        }
        return ValueUtil.convertObjectToMap(medicineModule.get());
    }

    @Override
    public void deleteMedicineModuleById(Integer idInstance) {
        medicineModuleRepository.deleteMedicineModuleById(idInstance);
    }

    @Override
    public MedicineModule findMedicineModuleByIdMedicine(Integer idInstance) {
        Optional<MedicineModule> medicineModule = medicineModuleRepository.findMedicineModuleById(idInstance);
        if (!medicineModule.isPresent()) {
            throw new NotFoundException("Don't exits medicine module by id");
        }
        return medicineModule.get();
    }
}
