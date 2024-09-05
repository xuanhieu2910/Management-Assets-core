package com.example.csvccdshustbe.service.modules.medicineModule.impl;

import com.example.csvccdshustbe.entity.MedicineModule;
import com.example.csvccdshustbe.repository.medicineModule.MedicineModuleRepository;
import com.example.csvccdshustbe.service.modules.medicineModule.MedicineModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineModuleServiceImpl implements MedicineModuleService {

    @Autowired
    MedicineModuleRepository medicineModuleRepository;
    @Override
    public MedicineModule save(MedicineModule medicineModule) {
        return medicineModuleRepository.save(medicineModule);
    }
}
