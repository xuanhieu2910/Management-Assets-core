package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.entity.CarModule;
import com.example.csvccdshustbe.entity.IModules;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class CarModuleFactory implements ModuleFactory {

    @Override
    public IModules createModule(Map<String, Object> mapModuleCreate) {
        CarModule carModule = new CarModule();
        carModule.setIdAsset(ValueUtil.getIntegerByObject(mapModuleCreate.get("idAsset")));
        carModule.setIsFreeTax(ValueUtil.getIntegerByObject(mapModuleCreate.get("isFreeTax")));
        carModule.setValueTax(ValueUtil.getStringByObject(mapModuleCreate.get("valueTax")));
        carModule.setLicensePlate(ValueUtil.getStringByObject(mapModuleCreate.get("licensePlate")));
        carModule.setLabelCar(ValueUtil.getStringByObject(mapModuleCreate.get("labelCar")));
        carModule.setTypeCar(ValueUtil.getStringByObject(mapModuleCreate.get("typeCar")));
        carModule.setLoadCapacity(ValueUtil.getStringByObject(mapModuleCreate.get("loadCapacity")));
        carModule.setNumberSeats(ValueUtil.getIntegerByObject(mapModuleCreate.get("numberSeats")));
        carModule.setCapacity(ValueUtil.getStringByObject(mapModuleCreate.get("capacity")));
        carModule.setCylinderCapacity(ValueUtil.getStringByObject(mapModuleCreate.get("cylinderCapacity")));
        carModule.setClutchNumber(ValueUtil.getStringByObject(mapModuleCreate.get("clutchNumber")));
        carModule.setVehicleIdentificationNumber(ValueUtil.getStringByObject(mapModuleCreate.get("vehicleIdentificationNumber")));
        carModule.setMachineNumber(ValueUtil.getStringByObject(mapModuleCreate.get("machineNumber")));
        carModule.setPublishYear(ValueUtil.getStringByObject(mapModuleCreate.get("publishYear")));
        carModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleCreate.get("idCountryProducer")));
        carModule.setLicenseCertificateRegister(ValueUtil.getStringByObject(mapModuleCreate.get("licenseCertificateRegister")));
        carModule.setPublishDateLicense(ValueUtil.getStringByObject(mapModuleCreate.get("publishDateLicense")));
        carModule.setCompanyRegister(ValueUtil.getStringByObject(mapModuleCreate.get("companyRegister")));
        carModule.setSource(ValueUtil.getStringByObject(mapModuleCreate.get("source")));
        carModule.setColor(ValueUtil.getStringByObject(mapModuleCreate.get("color")));
        carModule.setIdUser(ValueUtil.getIntegerByObject(mapModuleCreate.get("idUser")));
        carModule.setIdTypeUse(ValueUtil.getIntegerByObject(mapModuleCreate.get("idTypeUse")));
        String timeCurrent = String.valueOf(new Date().getTime());
        carModule.setTimeCreated(timeCurrent);
        carModule.setTimeModified(timeCurrent);
        return carModule;
    }
}
