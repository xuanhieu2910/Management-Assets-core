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
import com.example.csvccdshustbe.service.modules.otherVehicleTransportModule.OtherVehicleTransportModuleService;
import com.example.csvccdshustbe.service.modules.treeAndAnimalModule.TreeAndAnimalModuleService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
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
    @Autowired
    OtherVehicleTransportModuleService otherVehicleTransportModuleService;
    @Autowired
    ModulesService modulesService;



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
            case OtherAssetModule -> {
                idInstance = otherAssetModuleService.save((OtherAssetModule) modules).getIdOtherAssetModule();
            }
            case OtherVehicleTransportModule -> {
                idInstance = otherVehicleTransportModuleService.save((OtherVehicleTransportModule) modules).getIdOtherVehicleTransportModule();
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

    public void validateDataModules(List<Map<String, Object>> dataModules) throws ValidateFiledException {
        if (CollectionUtils.isEmpty(dataModules)) {
            throw new ValidateFiledException("Validate data modules!");
        }
        for (Map<String, Object> data : dataModules) {
            validateModules(data);
            proxyValidateDataModules(data);
        }
    }

    private void validateModules(Map<String, Object> data) {
        Modules modules = modulesService.findModulesByTypeModules(ValueUtil.getStringByObject(data.get(Constants.KEY_TYPE_MODULE)));
        data.put("idModule", modules.getIdModule());
    }

    public void proxyValidateDataModules(Map<String,Object> dataModule) throws ValidateFiledException {
        String typeModule = ValueUtil.getStringByObject(dataModule.get(Constants.KEY_TYPE_MODULE));
        EnumModuleFactory enumModuleFactory = Enum.valueOf(EnumModuleFactory.class, typeModule);
        switch (enumModuleFactory) {
            case MedicineModule -> {
                medicineModuleService.validateDataCreate(dataModule);
            }
            case MachineModule -> {
               machineModuleService.validateDataCreate(dataModule);
            }
            case HouseModule -> {
                houseModuleService.validateDataCreate(dataModule);
            }
            case GroundModule -> {
                groundModuleService.validateDataCreate(dataModule);
            }
            case CarModule -> {
                carModuleService.validateDataCreate(dataModule);
            }
            case TreeAndAnimalModule -> {
                treeAndAnimalModuleService.validateDataCreate(dataModule);
            }
            case ArchitectureModule -> {
                architectureModuleService.validateDataCreate(dataModule);
            }
            case OtherAssetModule -> {
                otherAssetModuleService.validateDataCreate(dataModule);
            }
            case OtherVehicleTransportModule -> {
                otherVehicleTransportModuleService.validateDataCreate(dataModule);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type architecture to validate!");
            }
        }
    }



    public Map<String,Object> findDataDetailByTypeModulesAndIdInstance(String typeModules, Integer idInstance) throws ValidateFiledException, IllegalAccessException {
        EnumModuleFactory enumModuleFactory = Enum.valueOf(EnumModuleFactory.class, typeModules);
        switch (enumModuleFactory) {
            case MedicineModule -> {
                return medicineModuleService.findMedicineModuleByIdMedicine(idInstance);
            }
            case MachineModule -> {
                return machineModuleService.findMachineModuleByIdMachineModule(idInstance);
            }
            case HouseModule -> {
                return houseModuleService.findHouseModuleByIdHouseModule(idInstance);
            }
            case GroundModule -> {
                return groundModuleService.findGroundModuleByIdGroundModule(idInstance);
            }
            case CarModule -> {
                return carModuleService.findCarModuleByIdCarModule(idInstance);
            }
            case TreeAndAnimalModule -> {
                return treeAndAnimalModuleService.findAnimalTreeModuleByIdAnimalTree(idInstance);
            }
            case ArchitectureModule -> {
                return architectureModuleService.findArchitectureModuleByIdArchitectureModule(idInstance);
            }
            case OtherAssetModule -> {
                return otherAssetModuleService.findOtherAssetModuleByIdOtherAssetModule(idInstance);
            }
            case OtherVehicleTransportModule -> {
                return otherVehicleTransportModuleService.findOtherVehicleTransportModuleByIdOtherVehicleTransport(idInstance);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type architecture to get data!");
            }
        }
    }
}
