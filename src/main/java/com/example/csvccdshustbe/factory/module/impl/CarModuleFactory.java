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
        carModule.setSparePartsAttack(ValueUtil.getStringByObject(mapModuleCreate.get("sparePartsAttack")));
        String timeCurrent = String.valueOf(new Date().getTime());
        carModule.setTimeCreated(timeCurrent);
        carModule.setTimeModified(timeCurrent);
        return carModule;
    }

    @Override
    public IModules updateModule(Map<String, Object> mapModuleUpdate, IModules iModules) {
        CarModule carModule = (CarModule) iModules;
        carModule.setIsFreeTax(ValueUtil.getIntegerByObject(mapModuleUpdate.get("isFreeTax")));
        carModule.setValueTax(ValueUtil.getStringByObject(mapModuleUpdate.get("valueTax")));
        carModule.setLicensePlate(ValueUtil.getStringByObject(mapModuleUpdate.get("licensePlate")));
        carModule.setLabelCar(ValueUtil.getStringByObject(mapModuleUpdate.get("labelCar")));
        carModule.setTypeCar(ValueUtil.getStringByObject(mapModuleUpdate.get("typeCar")));
        carModule.setLoadCapacity(ValueUtil.getStringByObject(mapModuleUpdate.get("loadCapacity")));
        carModule.setNumberSeats(ValueUtil.getIntegerByObject(mapModuleUpdate.get("numberSeats")));
        carModule.setCapacity(ValueUtil.getStringByObject(mapModuleUpdate.get("capacity")));
        carModule.setCylinderCapacity(ValueUtil.getStringByObject(mapModuleUpdate.get("cylinderCapacity")));
        carModule.setClutchNumber(ValueUtil.getStringByObject(mapModuleUpdate.get("clutchNumber")));
        carModule.setVehicleIdentificationNumber(ValueUtil.getStringByObject(mapModuleUpdate.get("vehicleIdentificationNumber")));
        carModule.setMachineNumber(ValueUtil.getStringByObject(mapModuleUpdate.get("machineNumber")));
        carModule.setPublishYear(ValueUtil.getStringByObject(mapModuleUpdate.get("publishYear")));
        carModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idCountryProducer")));
        carModule.setLicenseCertificateRegister(ValueUtil.getStringByObject(mapModuleUpdate.get("licenseCertificateRegister")));
        carModule.setPublishDateLicense(ValueUtil.getStringByObject(mapModuleUpdate.get("publishDateLicense")));
        carModule.setCompanyRegister(ValueUtil.getStringByObject(mapModuleUpdate.get("companyRegister")));
        carModule.setSource(ValueUtil.getStringByObject(mapModuleUpdate.get("source")));
        carModule.setColor(ValueUtil.getStringByObject(mapModuleUpdate.get("color")));
        carModule.setIdUser(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idUser")));
        carModule.setIdTypeUse(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idTypeUse")));
        carModule.setSparePartsAttack(ValueUtil.getStringByObject(mapModuleUpdate.get("sparePartsAttack")));
        String timeCurrent = String.valueOf(new Date().getTime());
        carModule.setTimeModified(timeCurrent);
        return carModule;
    }
}
