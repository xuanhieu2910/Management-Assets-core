package com.example.csvccdshustbe.service.modules;

import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.EnumModuleFactory;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.service.modules.architectureModule.ArchitectureModuleService;
import com.example.csvccdshustbe.service.modules.assetModules.AssetModulesService;
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

import java.util.Date;
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
    @Autowired
    AssetModulesService assetModulesService;


    public void save(IModules modules, Map<String,Object> moduleDataAsset) throws ValidateFiledException {
        String typeModule = ValueUtil.getStringByObject(moduleDataAsset.get(Constants.KEY_TYPE_MODULE));
        EnumModuleFactory enumModuleFactory = Enum.valueOf(EnumModuleFactory.class, typeModule);
        Integer idInstance;
        switch (enumModuleFactory) {
            case MedicineModule -> {
                idInstance =  medicineModuleService.save((MedicineModule) modules).getIdMedicineModule();
            }
            case MachineModule -> {
                idInstance = machineModuleService.save((MachineModule) modules).getIdMachineModule();
            }
            case HouseModule -> {
                idInstance = houseModuleService.save((HouseModule) modules).getIdHouseModule();
            }
            case GroundModule -> {
                idInstance = groundModuleService.saveGroundModule((GroundModule) modules).getIdGroundModule();
            }
            case CarModule -> {
                idInstance = carModuleService.save((CarModule) modules).getIdCarModule();
            }
            case TreeAndAnimalModule -> {
                idInstance = treeAndAnimalModuleService.save((AnimalTreeModule) modules).getIdAnimalTreeModule();
            }
            case ArchitectureModule -> {
                idInstance = architectureModuleService.save((ArchitectureModule) modules).getIdArchitectureModule();
            }
            default -> {
                throw new ValidateFiledException("Don't exits type architecture!");
            }
        }
        assetModulesService.save(createAssetModules(moduleDataAsset, idInstance));
    }

    private AssetModules createAssetModules(Map<String,Object> moduleDataAsset, Integer idInstance ){
        AssetModules assetModules = new AssetModules();
        assetModules.setIdAsset(ValueUtil.getIntegerByObject(moduleDataAsset.get("idAsset")));
        assetModules.setIdModule(ValueUtil.getIntegerByObject(moduleDataAsset.get("idModule")));
        assetModules.setIdInstance(idInstance);
        String timeCurrent = String.valueOf(new Date().getTime());
        assetModules.setTimeCreated(timeCurrent);
        assetModules.setTimeModified(timeCurrent);
        return assetModules;
    }

}
