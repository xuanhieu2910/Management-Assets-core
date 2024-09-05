package com.example.csvccdshustbe.service.modules;

import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.EnumDeclareFactory;
import com.example.csvccdshustbe.enums.EnumModuleFactory;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.factory.module.impl.*;
import com.example.csvccdshustbe.service.modules.architectureModule.ArchitectureModuleService;
import com.example.csvccdshustbe.service.modules.carModule.CarModuleService;
import com.example.csvccdshustbe.service.modules.groundModule.GroundModuleService;
import com.example.csvccdshustbe.service.modules.houseModule.HouseModuleService;
import com.example.csvccdshustbe.service.modules.machineModule.MachineModuleService;
import com.example.csvccdshustbe.service.modules.medicineModule.MedicineModuleService;
import com.example.csvccdshustbe.service.modules.otherAssetModule.OtherAssetModuleService;
import com.example.csvccdshustbe.service.modules.treeAndAnimalModule.TreeAndAnimalModuleService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ModulesServiceFactory {

    @Autowired
    ArchitectureModuleService architectureModuleService;
    @Autowired
    CarModuleService carModuleService;
    @Autowired
    HouseModuleService houseModuleService;
    @Autowired
    MachineModuleService machineModuleService;
    @Autowired
    MedicineModuleService medicineModuleService;
    @Autowired
    OtherAssetModuleService otherAssetModuleService;
    @Autowired
    TreeAndAnimalModuleService treeAndAnimalModuleService;
    @Autowired
    GroundModuleService groundModuleService;


    public IModules save(IModules modules, Map<String,Object> moduleDataAsset) throws ValidateFiledException {
        String typeModule = ValueUtil.getStringByObject(moduleDataAsset.get(Constants.KEY_TYPE_MODULE));
        EnumModuleFactory enumModuleFactory = Enum.valueOf(EnumModuleFactory.class, typeModule);
        switch (enumModuleFactory) {
            case MedicineModule -> {
                return medicineModuleService.save((MedicineModule) modules);
            }
            case MachineModule -> {
                return machineModuleService.save((MachineModule) modules);
            }
            case HouseModule -> {
                return houseModuleService.save((HouseModule) modules);
            }
            case GroundModule -> {
                return groundModuleService.saveGroundModule((GroundModule) modules);
            }
            case CarModule -> {
                return carModuleService.save((CarModule) modules);
            }
            case TreeAndAnimalModule -> {
                return treeAndAnimalModuleService.save((AnimalTreeModule) modules);
            }
            case ArchitectureModule -> {
                return architectureModuleService.save((ArchitectureModule) modules);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type architecture!");
            }
        }
    }

}
